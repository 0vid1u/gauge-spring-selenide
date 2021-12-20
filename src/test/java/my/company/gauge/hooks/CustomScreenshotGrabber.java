package my.company.gauge.hooks;

import com.codeborne.selenide.WebDriverRunner;
import com.thoughtworks.gauge.screenshot.CustomScreenshotWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import static java.awt.image.BufferedImage.TYPE_BYTE_GRAY;
import static java.lang.Boolean.parseBoolean;
import static java.lang.System.getenv;

@Slf4j
public class CustomScreenshotGrabber implements CustomScreenshotWriter {

    @Override
    public String takeScreenshot() {
        return saveScreenshotToFile();
    }

    private String saveScreenshotToFile() {
        final String fileName = "screenshot-" + UUID.randomUUID() + ".png";
        final File screenshotFile = new File(Paths.get(getenv("gauge_screenshots_dir"), fileName).toString());
        File tmpFile = captureScreenshot(WebDriverRunner.getWebDriver(), OutputType.FILE).orElseThrow();

        try {
            if (parseBoolean(getenv("enable_monochrome_screenshots"))) {
                convertToBlackAndWhite(tmpFile);
            }
            FileUtils.copyFile(tmpFile, screenshotFile);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return screenshotFile.getName();
    }

    private void convertToBlackAndWhite(File source) throws IOException {
        BufferedImage image;
        image = ImageIO.read(source);

        final BufferedImage blackAndWhiteImage = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                TYPE_BYTE_GRAY
        );

        final Graphics2D graphics = blackAndWhiteImage.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();

        ImageIO.write(blackAndWhiteImage, "png", source);
    }

    private <T> Optional<T> captureScreenshot(WebDriver driver, @SuppressWarnings("SameParameterValue") OutputType<T> outputType) {
        if (driver instanceof TakesScreenshot) {
            T screenshot = (driver instanceof RemoteWebDriver)
                    ? ((TakesScreenshot) new Augmenter().augment(driver)).getScreenshotAs(outputType)
                    : ((TakesScreenshot) driver).getScreenshotAs(outputType);
            return Optional.of(screenshot);
        }
        return Optional.empty();
    }
}

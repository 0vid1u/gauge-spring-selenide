package my.company.hooks;

import com.thoughtworks.gauge.screenshot.CustomScreenshotWriter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomScreenshotsFactory implements CustomScreenshotWriter {
    @Override
    public String takeScreenshot() {
//        if (DriverManager.getInstance().getDriver() != null) {
//            return getScreenshotFile().getName();
//        } else {
//            Gauge.writeMessage("Skipping screenshots as UI driver doesn't exist");
//            log.warn("Skipping screenshots as UI driver doesn't exist");
//            return null;
//        }
        return null;
    }

//    private File getScreenshotFile() {
//        final String fileName = "screenshot-" + UUID.randomUUID().toString() + ".png";
//        File tmpFile, screenshotFile = new File(Paths.get(System.getenv("gauge_screenshots_dir"), fileName).toString());
//
//        if (!(DriverManager.getInstance().getDriver() instanceof RemoteWebDriver)) {
//            tmpFile = ((TakesScreenshot) DriverManager.getInstance().getDriver())
//                    .getScreenshotAs(OutputType.FILE);
//        } else {
//            Augmenter augmenter = new Augmenter();
//            tmpFile = ((TakesScreenshot) augmenter.augment(DriverManager.getInstance().getDriver()))
//                    .getScreenshotAs(OutputType.FILE);
//        }
//
//        try {
//            FileUtils.copyFile(tmpFile, screenshotFile);
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//            e.printStackTrace();
//        }
//        return screenshotFile;
//    }
}

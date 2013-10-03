package ch.baal.foodl.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Loats default properties for the Project.
 */
public class FoodlProperties {

    private static Logger LOG = LoggerFactory.getLogger(Foodl.class);

    private Properties defaultProps = new Properties();

    public FoodlProperties() {
        try (FileInputStream in = new FileInputStream(FoodlConstants.FOODL_PROPERTIES)) {
            defaultProps.load(in);
        } catch (IOException e) {
            LOG.warn("Could not load Property file: '{}' ", FoodlConstants.FOODL_PROPERTIES, e);
        }
    }


    /**
     * Returns the Project Version.
     * @return Project Version String
     */
    public String getProjectVersion() {
        return defaultProps.getProperty("application.version");
    }
}

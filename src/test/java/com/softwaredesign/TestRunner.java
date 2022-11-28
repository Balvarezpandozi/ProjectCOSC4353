package com.softwaredesign;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({NetworkInterfaceHandlerTest.class, PcapFileWriterAndReaderTest.class})
public class TestRunner {
    //So far this class just helps running all tests. No need to write any code in here.
}

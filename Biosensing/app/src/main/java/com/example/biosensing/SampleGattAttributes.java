/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.biosensing;

import java.util.HashMap;

/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
public class SampleGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();
    public static String HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb";
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
    public static String IR_TEMP_DATA = "f000aa01-0451-4000-b000-000000000000";
    public static String HUMIDITY_DATA = "f000aa21-0451-4000-b000-000000000000";
    public static String BAROMETRIC_DATA = "f000aa41-0451-4000-b000-000000000000";
    public static String TEMPERATURE_MEASUREMENT = "f0002a1c-0000-1000-8000-00805f9b34fb";


    static {
        // Sample Services.
        attributes.put("0000180d-0000-1000-8000-00805f9b34fb", "Heart Rate Service");
        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service");
        attributes.put("f000aa00-0451-4000-b000-000000000000", "IR Temperature Service");
        attributes.put("f000aa20-0451-4000-b000-000000000000", "Humidity Service");
        attributes.put("f000aa40-0451-4000-b000-000000000000", "Barometric Service");
        attributes.put("f0001809-0000-1000-8000-00805f9b34fb", "Health Thermometer Service");
        attributes.put("00001800-0000-1000-8000-00805f9b34fb", "Generic Access Service");
        attributes.put("00001801-0000-1000-8000-00805f9b34fb", "Generic Attribute Service");
        // Sample Characteristics.
        attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
        attributes.put(IR_TEMP_DATA, "IR Temperature Data");
        attributes.put(HUMIDITY_DATA, "Humidity Data");
        attributes.put(BAROMETRIC_DATA, "Barometric Data");
        attributes.put(TEMPERATURE_MEASUREMENT, "Temperature Measurement");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
        attributes.put("00002a00-0000-1000-8000-00805f9b34fb", "Device Name");
        attributes.put("00002a05-0000-1000-8000-00805f9b34fb", "Service Changed");
    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}

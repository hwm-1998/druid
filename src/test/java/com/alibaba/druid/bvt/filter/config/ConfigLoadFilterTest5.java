/*
 * Copyright 1999-2011 Alibaba Group Holding Ltd.
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
package com.alibaba.druid.bvt.filter.config;

import java.sql.Connection;

import com.alibaba.druid.filter.config.ConfigFilter;
import com.alibaba.druid.mock.MockDriver;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.JdbcUtils;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ConfigLoadFilterTest5 extends TestCase {

    private DruidDataSource dataSource;

    protected void setUp() throws Exception {
        System.setProperty(ConfigFilter.SYS_PROP_CONFIG_KEY,
                           "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMmVkLy+Cy/3rQ1OyH7nz1tT8Bu2KEU+z5LbeZ1yue/RN4KlcOEalj/K9Ev05Lpfu9cOdYnYM8ka1MiQdHLyJP0CAwEAAQ==");

        System.setProperty(ConfigFilter.SYS_PROP_CONFIG_ENCRYPTED_PASSWORD,
                           "TFxB6eJcgxE1hxOgiwOC/L7zWR/9vnSIfpggI2PTfcvvRhSnCGCPwI9n03fiJiLmRdnDU2/KaVTJYwz8zzkBqg==");
        dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mock:config-3");
        dataSource.setMaxActive(13);
        dataSource.setFilters("config");
        dataSource.setTestOnBorrow(false);
    }

    protected void tearDown() throws Exception {
        JdbcUtils.close(dataSource);
        System.clearProperty(ConfigFilter.SYS_PROP_CONFIG_KEY);
        System.clearProperty(ConfigFilter.SYS_PROP_CONFIG_ENCRYPTED_PASSWORD);
    }

    public void test_config() throws Exception {
        dataSource.init();

        Assert.assertEquals(13, dataSource.getMaxActive());
        Assert.assertEquals("jdbc:mock:config-3", dataSource.getUrl());
        Assert.assertEquals(MockDriver.instance, dataSource.getDriver());
        Assert.assertEquals("abc", dataSource.getPassword());

        Connection conn = dataSource.getConnection();
        conn.close();
    }
}
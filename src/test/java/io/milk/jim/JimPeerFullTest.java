/*
 * Copyright 2018 Sid Narayan
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package io.milk.jim;

import org.junit.*;
import junit.framework.TestCase;

import java.io.*;
import java.sql.*;

public class JimPeerFullTest extends TestCase {
    JimPeerFull jimmy;

    @Before
    public void setUp() throws Exception {
        jimmy = new JimPeerFull("jimmy@milk.io");
    }

    @AfterClass
    public void tearDown() throws Exception {
        jimmy.stop();
        File db = new File("/Users/milk/pamtest.db");
        if(db.exists())
            db.delete();
    }

    /**
     * Check that the database and the peers table are created
     */
    @Test
    public void testInitDatabase() throws Exception {
        jimmy.initDatabase("pamtest.db");
        File db = new File("/Users/milk/pamtest.db");
        assertTrue(db.exists());
        Connection conn = jimmy.getConn();
        DatabaseMetaData dbm = conn.getMetaData();
        ResultSet rs = dbm.getTables(null, null, "peers", null);
        assertTrue(rs.next());
    }

    public void testInsert() {
    }

    public void testUpdate() {
    }

    public void testInsertOrUpdate() {
    }
}
/*
 * Copyright 2018 Sid Narayan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.milk.jim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class JimPeerFull extends JimPeer {
    private static final Logger LOG = LoggerFactory.getLogger(JimPeerFull.class);

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS peers (\n"
            + "	id blob PRIMARY KEY,\n"
            + "	ip text NOT NULL\n"
            + ");";
    private static final String SELECT_NODE = "SELECT * FROM peers WHERE id = ?";
    private static final String INSERT_NODE = "INSERT INTO peers(id,ip) VALUES(?,?)";
    private static final String UPDATE_NODE = "UPDATE peers SET ip = ? WHERE id = ?";

    private Connection conn;


    public JimPeerFull(String username) {
        super(username);
        // TODO periodically check if info is stale
        // initDatabase("pam.db");
        // insertOrUpdate(me.getId(), me.getHost());
    }

    public void initDatabase(String fileName) {
        String url = "jdbc:sqlite:/Users/milk/" + fileName;
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                LOG.info("A new database has been created.");
                Statement stmt = conn.createStatement();
                stmt.execute(CREATE_TABLE);
            }
        } catch (SQLException e) {
            LOG.debug(e.getMessage());
        }
    }

    public void insert(byte[] id, String ip_addr) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(INSERT_NODE);
            pstmt.setBytes(1, id);
            pstmt.setString(2, ip_addr);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOG.debug(e.getMessage());
        }
    }

    public void update(byte[] id, String ip_addr) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(UPDATE_NODE);
            pstmt.setString(1, ip_addr);
            pstmt.setBytes(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOG.debug(e.getMessage());
        }
    }

    public void insertOrUpdate(byte[] id, String ip_addr) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(SELECT_NODE);
            pstmt.setBytes(1, id);
            ResultSet rs =  pstmt.executeQuery();
            // Update if a result is found, otherwise insert a new row.
            if (rs.next()) {
                update(id, ip_addr);
            } else {
                insert(id, ip_addr);
            }
        } catch (SQLException e) {
            LOG.debug(e.getMessage());
        }
    }

    @Override
    public void stop() {
        super.stop();
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            LOG.debug(e.getMessage());
        }
    }

    public Connection getConn() {
        return conn;
    }
}

package com.megamainmeeting;

import com.megamainmeeting.domain.error.BadDataException;
import com.megamainmeeting.domain.error.UserNotFoundException;
import com.megamainmeeting.push.UserPushTokenNotFound;
import com.megamainmeeting.push.UserPushTokenRepository;

import java.sql.*;
import java.util.*;

class UserPushRepository implements UserPushTokenRepository {

    private Properties properties = System.getProperties();
    private final String user = "postgres";
    private final String password = "1234";
    private final String jdbcPath = "jdbc:postgresql://localhost:5433/megamainmeeting";
    private final String GET_TOKEN_SQL = "Select token from user_push_token where user_id = Any(?)";

    public UserPushRepository() {

    }


    @Override
    public void addToken(long userId, String token) throws UserNotFoundException, BadDataException {

    }

    @Override
    public List<String> getTokens(long userId) throws UserPushTokenNotFound {
        try (Connection conn = DriverManager.getConnection(jdbcPath, user, password)) {
            PreparedStatement ps = conn.prepareStatement(GET_TOKEN_SQL);
            ps.setLong(1, userId);
            try (ResultSet resultSet = ps.executeQuery()) {
                return Collections.emptyList();
            } catch (Exception e) {
                throw new UserPushTokenNotFound();
            }
        } catch (SQLException e) {
            throw new UserPushTokenNotFound();
        }
    }

    @Override
    public Collection<String> getTokens(Set<Long> users) {
        try (Connection conn = DriverManager.getConnection(jdbcPath, user, password)) {
            PreparedStatement ps = conn.prepareStatement(GET_TOKEN_SQL);
            ps.setArray(1, conn.createArrayOf("bigint", users.toArray()));
            try (ResultSet resultSet = ps.executeQuery()) {
                List<String> pushTokens = new ArrayList<>();
                while (resultSet.next()) {
                    pushTokens.add(resultSet.getString(1));
                }
                return pushTokens;
            } catch (Exception e) {
                return Collections.emptyList();
            }
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }
}

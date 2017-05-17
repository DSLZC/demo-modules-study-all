package com.cas.server;

import com.distinct.cas.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;

/**
 * Created by dongsilin on 2017/5/16.
 * 自定义密码认证，利用shiro 加盐，多次加密进行密码比对
 */
public class AuthenticationDefinedHandler extends AbstractJdbcUsernamePasswordAuthenticationHandler {

    /** 查询密码sql */
    private String sql;
    /** 是否需要加盐 */
    private Boolean isSalt = false;
    /** 加密方式 */
    private String encryptType = "md5";
    /** 加密次数 */
    private int encryptTimes = 1;

    public AuthenticationDefinedHandler() {}

    protected final HandlerResult authenticateUsernamePasswordInternal(UsernamePasswordCredential credential) throws GeneralSecurityException, PreventedException {
        String username = credential.getUsername();

        // 利用shiro 加盐加密
        String encryptedPassword = isSalt? new SimpleHash(encryptType, credential.getPassword(), ByteSource.Util.bytes(username), encryptTimes).toHex() : new SimpleHash(encryptType, credential.getPassword()).toHex();

        System.out.println("======= input username:" + username + ", input password:" + credential.getPassword() + ", encryptedPassword:" + encryptedPassword);

        try {
            String e = this.getJdbcTemplate().queryForObject(this.sql, String.class, username);

            if(!e.trim().equals(encryptedPassword)) throw new FailedLoginException("Password does not match value on record.");
        } catch (IncorrectResultSizeDataAccessException var5) {
            if(var5.getActualSize() == 0) {
                throw new AccountNotFoundException(username + " not found with SQL query");
            }

            var5.printStackTrace();
            throw new FailedLoginException("Multiple records found for " + username);
        } catch (DataAccessException var6) {
            var6.printStackTrace();
            throw new PreventedException("SQL exception while executing query for " + username, var6);
        }

        return this.createHandlerResult(credential, new SimplePrincipal(username), null);
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Boolean getSalt() {
        return isSalt;
    }

    public void setSalt(Boolean salt) {
        isSalt = salt;
    }

    public String getEncryptType() {
        return encryptType;
    }

    public void setEncryptType(String encryptType) {
        this.encryptType = encryptType;
    }

    public int getEncryptTimes() {
        return encryptTimes;
    }

    public void setEncryptTimes(int encryptTimes) {
        this.encryptTimes = encryptTimes;
    }
}


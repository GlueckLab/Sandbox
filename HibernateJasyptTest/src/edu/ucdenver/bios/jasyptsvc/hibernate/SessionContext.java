/*
 * Web service utility functions for managing hibernate, json, etc.
 *
 * Copyright (C) 2010 Regents of the University of Colorado.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA  02110-1301, USA.
 */
package edu.ucdenver.bios.jasyptsvc.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.jasypt.hibernate3.encryptor.HibernatePBEEncryptorRegistry;


/**
 * Singleton class for creating a hibernate session factory object.
 *
 * @author Uttara Sakhadeo
 */
public final class SessionContext {
    // singleton instance variable
    /** The instance. */
    private static SessionContext instance = null;

    /** The session factory. */
    private SessionFactory sessionFactory = null;

    /**
     * Instantiates a new session context.
     *
     * @throws SessionContextException
     *             the session context exception
     */
    private SessionContext() throws SessionContextException {
        /*
         * Build a SessionFactory object from session-factory configuration
         * defined in the hibernate.cfg.xml file. In this file we register the
         * JDBC connection information, connection pool, the hibernate dialect
         * that we used and the mapping to our hbm.xml file for each POJO (Plain
         * Old Java Object).
         */
        try
        {
        /* Begin : Jasypt Chnages */
            /*StandardPBEStringEncryptor strongEncryptor = new StandardPBEStringEncryptor();
            strongEncryptor.setAlgorithm("PBEWithMD5AndDES");           
            strongEncryptor.setPassword("password");
            HibernatePBEEncryptorRegistry registry = HibernatePBEEncryptorRegistry.getInstance();
            registry.registerPBEStringEncryptor("configurationHibernateEncryptor", strongEncryptor);*/
            
            EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
            config.setPasswordEnvName("ENV_VARIABLE");
            
            StandardPBEStringEncryptor strongEncryptor = new StandardPBEStringEncryptor();
            strongEncryptor.setAlgorithm("PBEWithMD5AndDES");
            strongEncryptor.setConfig(config);
            
            HibernatePBEEncryptorRegistry registry =  HibernatePBEEncryptorRegistry.getInstance();
            registry.registerPBEStringEncryptor("configurationHibernateEncryptor", strongEncryptor);
            
        /* End : Jasypt Chnages */
        Configuration configuration = new Configuration();
        sessionFactory = configuration.configure().buildSessionFactory();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get the hibernate session factory object.
     *
     * @return session factory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Get the current hibernate session.
     *
     * @return session
     */
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Open a new hibernate session.
     *
     * @return session
     */
    public Session openSession() {
        return sessionFactory.openSession();
    }

    /*
     * Get an instance of the session context
     */
    /**
     * Gets the single instance of SessionContext.
     *
     * @return single instance of SessionContext
     * @throws SessionContextException
     *             the session context exception
     */
    public static synchronized SessionContext getInstance()
            throws SessionContextException {
        if (instance == null) {
            instance = new SessionContext();
        }
        return instance;
    }

}

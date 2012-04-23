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
package edu.ucdenver.bios.jasyptsvc4.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Base class for interacting with a database through hibernate. The BaseManager
 * is a wrapper class on standard hibernate calls for managing transactions,
 * commit/rollback, etc.
 *
 * @author Uttara Sakhadeo
 *
 */
public class BaseManager {

    /** The session. */
    protected Session session = null;

    /** The transaction started. */
    protected boolean transactionStarted = false;

    /**
     * Create a new hibernate manager.
     *
     * @throws BaseManagerException
     *             the base manager exception
     */
    public BaseManager() throws BaseManagerException {
        try {
            session = SessionContext.getInstance().getCurrentSession();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseManagerException("Failed to get database session: "
                    + e.getMessage());
        }
    }
        
    /**
     * Start a new transaction with the database.
     *
     * @throws BaseManagerException
     *             the base manager exception
     */
    public final void beginTransaction() throws BaseManagerException {
        try {
            if (session != null) {               
                session.beginTransaction();
                transactionStarted = true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BaseManagerException("Failed to get database session: "
                    + e.getMessage());
        }
    }
    
    public final Transaction getTransaction() throws BaseManagerException {
        Transaction transaction = null;
        try {            
            if (session != null) {               
                transaction = session.getTransaction();
                transactionStarted = true;
            }
        } catch (Exception e) {
            throw new BaseManagerException("Failed to get database session: "
                    + e.getMessage());
        }
        return transaction;
    }

    /**
     * Commit changes to the database.
     *
     * @throws BaseManagerException
     *             the base manager exception
     */
    public final void commit() throws BaseManagerException {
        try {
            if (session != null) {
                session.getTransaction().commit();
            }
            transactionStarted = false;
        } catch (Exception e) {
            throw new BaseManagerException("Failed to commit transaction: "
                    + e.getMessage());
        }
    }

    /**
     * Rollback database changes.
     *
     * @throws BaseManagerException
     *             the base manager exception
     */
    public final void rollback() throws BaseManagerException {
        try {
            if (session != null) {
                session.getTransaction().rollback();
            }
            transactionStarted = false;
        } catch (Exception e) {
            throw new BaseManagerException("Failed to rollback transaction: "
                    + e.getMessage());
        }
    }
}

/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.hibernate.ejb.test.ejb3configuration.id;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.framework.TestCase;

import org.hibernate.ejb.AvailableSettings;
import org.hibernate.ejb.Ejb3Configuration;

/**
 * @author Emmanuel Bernard <emmanuel@hibernate.org>
 */
public class IdentifierGeneratorStrategyProviderTest extends TestCase {
	public void testIdentifierGeneratorStrategyProvider() {
        Ejb3Configuration conf = new Ejb3Configuration();
        conf.setProperty(
				AvailableSettings.IDENTIFIER_GENERATOR_STRATEGY_PROVIDER,
				FunkyIdentifierGeneratorProvider.class.getName() );
        conf.addAnnotatedClass( Cable.class );
		final EntityManagerFactory entityManagerFactory = conf.buildEntityManagerFactory();
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
			entityManager.persist( new Cable() );
			entityManager.flush();
            fail("FunkyException should have been thrown when the id is generated");
        }
        catch ( FunkyException e ) {
			entityManager.close();
            entityManagerFactory.close();
        }
    }
}

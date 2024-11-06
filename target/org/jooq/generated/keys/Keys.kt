/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated.keys


import org.jooq.UniqueKey
import org.jooq.generated.tables.Databasechangeloglock
import org.jooq.generated.tables.Passwordmanagement
import org.jooq.generated.tables.User
import org.jooq.generated.tables.records.DatabasechangeloglockRecord
import org.jooq.generated.tables.records.PasswordmanagementRecord
import org.jooq.generated.tables.records.UserRecord
import org.jooq.impl.DSL
import org.jooq.impl.Internal



// -------------------------------------------------------------------------
// UNIQUE and PRIMARY KEY definitions
// -------------------------------------------------------------------------

val KEY_DATABASECHANGELOGLOCK_PRIMARY: UniqueKey<DatabasechangeloglockRecord> = Internal.createUniqueKey(Databasechangeloglock.DATABASECHANGELOGLOCK, DSL.name("KEY_DATABASECHANGELOGLOCK_PRIMARY"), arrayOf(Databasechangeloglock.DATABASECHANGELOGLOCK.ID), true)
val KEY_PASSWORDMANAGEMENT_PRIMARY: UniqueKey<PasswordmanagementRecord> = Internal.createUniqueKey(Passwordmanagement.PASSWORDMANAGEMENT, DSL.name("KEY_PasswordManagement_PRIMARY"), arrayOf(Passwordmanagement.PASSWORDMANAGEMENT.ID), true)
val KEY_USER_PRIMARY: UniqueKey<UserRecord> = Internal.createUniqueKey(User.USER, DSL.name("KEY_User_PRIMARY"), arrayOf(User.USER.USERID), true)
val KEY_USER_UNIQUE_EMAIL_CONSTRAINT: UniqueKey<UserRecord> = Internal.createUniqueKey(User.USER, DSL.name("KEY_User_unique_email_constraint"), arrayOf(User.USER.EMAIL), true)

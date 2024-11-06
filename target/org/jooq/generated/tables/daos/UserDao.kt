/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated.tables.daos


import kotlin.collections.List

import org.jooq.Configuration
import org.jooq.generated.tables.User
import org.jooq.generated.tables.records.UserRecord
import org.jooq.impl.DAOImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class UserDao(configuration: Configuration?) : DAOImpl<UserRecord, org.jooq.generated.tables.pojos.User, Int>(User.USER, org.jooq.generated.tables.pojos.User::class.java, configuration) {

    /**
     * Create a new UserDao without any configuration
     */
    constructor(): this(null)

    override fun getId(o: org.jooq.generated.tables.pojos.User): Int? = o.userid

    /**
     * Fetch records that have <code>userId BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfUserid(lowerInclusive: Int?, upperInclusive: Int?): List<org.jooq.generated.tables.pojos.User> = fetchRange(User.USER.USERID, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>userId IN (values)</code>
     */
    fun fetchByUserid(vararg values: Int): List<org.jooq.generated.tables.pojos.User> = fetch(User.USER.USERID, *values.toTypedArray())

    /**
     * Fetch a unique record that has <code>userId = value</code>
     */
    fun fetchOneByUserid(value: Int): org.jooq.generated.tables.pojos.User? = fetchOne(User.USER.USERID, value)

    /**
     * Fetch records that have <code>firstName BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfFirstname(lowerInclusive: String?, upperInclusive: String?): List<org.jooq.generated.tables.pojos.User> = fetchRange(User.USER.FIRSTNAME, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>firstName IN (values)</code>
     */
    fun fetchByFirstname(vararg values: String): List<org.jooq.generated.tables.pojos.User> = fetch(User.USER.FIRSTNAME, *values)

    /**
     * Fetch records that have <code>lastName BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfLastname(lowerInclusive: String?, upperInclusive: String?): List<org.jooq.generated.tables.pojos.User> = fetchRange(User.USER.LASTNAME, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>lastName IN (values)</code>
     */
    fun fetchByLastname(vararg values: String): List<org.jooq.generated.tables.pojos.User> = fetch(User.USER.LASTNAME, *values)

    /**
     * Fetch records that have <code>age BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfAge(lowerInclusive: String?, upperInclusive: String?): List<org.jooq.generated.tables.pojos.User> = fetchRange(User.USER.AGE, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>age IN (values)</code>
     */
    fun fetchByAge(vararg values: String): List<org.jooq.generated.tables.pojos.User> = fetch(User.USER.AGE, *values)

    /**
     * Fetch records that have <code>phone BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfPhone(lowerInclusive: String?, upperInclusive: String?): List<org.jooq.generated.tables.pojos.User> = fetchRange(User.USER.PHONE, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>phone IN (values)</code>
     */
    fun fetchByPhone(vararg values: String): List<org.jooq.generated.tables.pojos.User> = fetch(User.USER.PHONE, *values)

    /**
     * Fetch records that have <code>email BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfEmail(lowerInclusive: String?, upperInclusive: String?): List<org.jooq.generated.tables.pojos.User> = fetchRange(User.USER.EMAIL, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>email IN (values)</code>
     */
    fun fetchByEmail(vararg values: String): List<org.jooq.generated.tables.pojos.User> = fetch(User.USER.EMAIL, *values)

    /**
     * Fetch a unique record that has <code>email = value</code>
     */
    fun fetchOneByEmail(value: String): org.jooq.generated.tables.pojos.User? = fetchOne(User.USER.EMAIL, value)

    /**
     * Fetch records that have <code>password BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfPassword(lowerInclusive: String?, upperInclusive: String?): List<org.jooq.generated.tables.pojos.User> = fetchRange(User.USER.PASSWORD, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>password IN (values)</code>
     */
    fun fetchByPassword(vararg values: String): List<org.jooq.generated.tables.pojos.User> = fetch(User.USER.PASSWORD, *values)
}

/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated.tables


import java.time.LocalDateTime
import java.util.function.Function

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Name
import org.jooq.Record
import org.jooq.Records
import org.jooq.Row4
import org.jooq.Schema
import org.jooq.SelectField
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.generated.Airlinereservationsystem
import org.jooq.generated.keys.KEY_DATABASECHANGELOGLOCK_PRIMARY
import org.jooq.generated.tables.records.DatabasechangeloglockRecord
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class Databasechangeloglock(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, DatabasechangeloglockRecord>?,
    aliased: Table<DatabasechangeloglockRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<DatabasechangeloglockRecord>(
    alias,
    Airlinereservationsystem.AIRLINERESERVATIONSYSTEM,
    child,
    path,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of
         * <code>AirlineReservationSystem.DATABASECHANGELOGLOCK</code>
         */
        val DATABASECHANGELOGLOCK: Databasechangeloglock = Databasechangeloglock()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<DatabasechangeloglockRecord> = DatabasechangeloglockRecord::class.java

    /**
     * The column
     * <code>AirlineReservationSystem.DATABASECHANGELOGLOCK.ID</code>.
     */
    val ID: TableField<DatabasechangeloglockRecord, Int?> = createField(DSL.name("ID"), SQLDataType.INTEGER.nullable(false), this, "")

    /**
     * The column
     * <code>AirlineReservationSystem.DATABASECHANGELOGLOCK.LOCKED</code>.
     */
    val LOCKED: TableField<DatabasechangeloglockRecord, Byte?> = createField(DSL.name("LOCKED"), SQLDataType.TINYINT.nullable(false), this, "")

    /**
     * The column
     * <code>AirlineReservationSystem.DATABASECHANGELOGLOCK.LOCKGRANTED</code>.
     */
    val LOCKGRANTED: TableField<DatabasechangeloglockRecord, LocalDateTime?> = createField(DSL.name("LOCKGRANTED"), SQLDataType.LOCALDATETIME(0), this, "")

    /**
     * The column
     * <code>AirlineReservationSystem.DATABASECHANGELOGLOCK.LOCKEDBY</code>.
     */
    val LOCKEDBY: TableField<DatabasechangeloglockRecord, String?> = createField(DSL.name("LOCKEDBY"), SQLDataType.VARCHAR(255), this, "")

    private constructor(alias: Name, aliased: Table<DatabasechangeloglockRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<DatabasechangeloglockRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased
     * <code>AirlineReservationSystem.DATABASECHANGELOGLOCK</code> table
     * reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased
     * <code>AirlineReservationSystem.DATABASECHANGELOGLOCK</code> table
     * reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>AirlineReservationSystem.DATABASECHANGELOGLOCK</code>
     * table reference
     */
    constructor(): this(DSL.name("DATABASECHANGELOGLOCK"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, DatabasechangeloglockRecord>): this(Internal.createPathAlias(child, key), child, key, DATABASECHANGELOGLOCK, null)
    override fun getSchema(): Schema? = if (aliased()) null else Airlinereservationsystem.AIRLINERESERVATIONSYSTEM
    override fun getPrimaryKey(): UniqueKey<DatabasechangeloglockRecord> = KEY_DATABASECHANGELOGLOCK_PRIMARY
    override fun `as`(alias: String): Databasechangeloglock = Databasechangeloglock(DSL.name(alias), this)
    override fun `as`(alias: Name): Databasechangeloglock = Databasechangeloglock(alias, this)
    override fun `as`(alias: Table<*>): Databasechangeloglock = Databasechangeloglock(alias.getQualifiedName(), this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Databasechangeloglock = Databasechangeloglock(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Databasechangeloglock = Databasechangeloglock(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): Databasechangeloglock = Databasechangeloglock(name.getQualifiedName(), null)

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row4<Int?, Byte?, LocalDateTime?, String?> = super.fieldsRow() as Row4<Int?, Byte?, LocalDateTime?, String?>

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    fun <U> mapping(from: (Int?, Byte?, LocalDateTime?, String?) -> U): SelectField<U> = convertFrom(Records.mapping(from))

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    fun <U> mapping(toType: Class<U>, from: (Int?, Byte?, LocalDateTime?, String?) -> U): SelectField<U> = convertFrom(toType, Records.mapping(from))
}
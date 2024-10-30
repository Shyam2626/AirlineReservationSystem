/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated.tables.records


import java.time.LocalDateTime

import org.jooq.Field
import org.jooq.Record1
import org.jooq.Record4
import org.jooq.Row4
import org.jooq.generated.tables.Databasechangeloglock
import org.jooq.impl.UpdatableRecordImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class DatabasechangeloglockRecord() : UpdatableRecordImpl<DatabasechangeloglockRecord>(Databasechangeloglock.DATABASECHANGELOGLOCK), Record4<Int?, Byte?, LocalDateTime?, String?> {

    open var id: Int?
        set(value): Unit = set(0, value)
        get(): Int? = get(0) as Int?

    open var locked: Byte?
        set(value): Unit = set(1, value)
        get(): Byte? = get(1) as Byte?

    open var lockgranted: LocalDateTime?
        set(value): Unit = set(2, value)
        get(): LocalDateTime? = get(2) as LocalDateTime?

    open var lockedby: String?
        set(value): Unit = set(3, value)
        get(): String? = get(3) as String?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<Int?> = super.key() as Record1<Int?>

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    override fun fieldsRow(): Row4<Int?, Byte?, LocalDateTime?, String?> = super.fieldsRow() as Row4<Int?, Byte?, LocalDateTime?, String?>
    override fun valuesRow(): Row4<Int?, Byte?, LocalDateTime?, String?> = super.valuesRow() as Row4<Int?, Byte?, LocalDateTime?, String?>
    override fun field1(): Field<Int?> = Databasechangeloglock.DATABASECHANGELOGLOCK.ID
    override fun field2(): Field<Byte?> = Databasechangeloglock.DATABASECHANGELOGLOCK.LOCKED
    override fun field3(): Field<LocalDateTime?> = Databasechangeloglock.DATABASECHANGELOGLOCK.LOCKGRANTED
    override fun field4(): Field<String?> = Databasechangeloglock.DATABASECHANGELOGLOCK.LOCKEDBY
    override fun component1(): Int? = id
    override fun component2(): Byte? = locked
    override fun component3(): LocalDateTime? = lockgranted
    override fun component4(): String? = lockedby
    override fun value1(): Int? = id
    override fun value2(): Byte? = locked
    override fun value3(): LocalDateTime? = lockgranted
    override fun value4(): String? = lockedby

    override fun value1(value: Int?): DatabasechangeloglockRecord {
        set(0, value)
        return this
    }

    override fun value2(value: Byte?): DatabasechangeloglockRecord {
        set(1, value)
        return this
    }

    override fun value3(value: LocalDateTime?): DatabasechangeloglockRecord {
        set(2, value)
        return this
    }

    override fun value4(value: String?): DatabasechangeloglockRecord {
        set(3, value)
        return this
    }

    override fun values(value1: Int?, value2: Byte?, value3: LocalDateTime?, value4: String?): DatabasechangeloglockRecord {
        this.value1(value1)
        this.value2(value2)
        this.value3(value3)
        this.value4(value4)
        return this
    }

    /**
     * Create a detached, initialised DatabasechangeloglockRecord
     */
    constructor(id: Int? = null, locked: Byte? = null, lockgranted: LocalDateTime? = null, lockedby: String? = null): this() {
        this.id = id
        this.locked = locked
        this.lockgranted = lockgranted
        this.lockedby = lockedby
        resetChangedOnNotNull()
    }

    /**
     * Create a detached, initialised DatabasechangeloglockRecord
     */
    constructor(value: org.jooq.generated.tables.pojos.Databasechangeloglock?): this() {
        if (value != null) {
            this.id = value.id
            this.locked = value.locked
            this.lockgranted = value.lockgranted
            this.lockedby = value.lockedby
            resetChangedOnNotNull()
        }
    }
}

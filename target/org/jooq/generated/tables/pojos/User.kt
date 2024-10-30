/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated.tables.pojos


import java.io.Serializable


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
data class User(
    var userid: Int? = null,
    var firstname: String? = null,
    var lastname: String? = null,
    var age: Int? = null,
    var phone: String? = null,
    var email: String? = null,
    var password: String? = null
): Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this::class != other::class)
            return false
        val o: User = other as User
        if (this.userid == null) {
            if (o.userid != null)
                return false
        }
        else if (this.userid != o.userid)
            return false
        if (this.firstname == null) {
            if (o.firstname != null)
                return false
        }
        else if (this.firstname != o.firstname)
            return false
        if (this.lastname == null) {
            if (o.lastname != null)
                return false
        }
        else if (this.lastname != o.lastname)
            return false
        if (this.age == null) {
            if (o.age != null)
                return false
        }
        else if (this.age != o.age)
            return false
        if (this.phone == null) {
            if (o.phone != null)
                return false
        }
        else if (this.phone != o.phone)
            return false
        if (this.email == null) {
            if (o.email != null)
                return false
        }
        else if (this.email != o.email)
            return false
        if (this.password == null) {
            if (o.password != null)
                return false
        }
        else if (this.password != o.password)
            return false
        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (if (this.userid == null) 0 else this.userid.hashCode())
        result = prime * result + (if (this.firstname == null) 0 else this.firstname.hashCode())
        result = prime * result + (if (this.lastname == null) 0 else this.lastname.hashCode())
        result = prime * result + (if (this.age == null) 0 else this.age.hashCode())
        result = prime * result + (if (this.phone == null) 0 else this.phone.hashCode())
        result = prime * result + (if (this.email == null) 0 else this.email.hashCode())
        result = prime * result + (if (this.password == null) 0 else this.password.hashCode())
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder("User (")

        sb.append(userid)
        sb.append(", ").append(firstname)
        sb.append(", ").append(lastname)
        sb.append(", ").append(age)
        sb.append(", ").append(phone)
        sb.append(", ").append(email)
        sb.append(", ").append(password)

        sb.append(")")
        return sb.toString()
    }
}

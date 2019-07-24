package by.next.way.spring.data.dialect

import org.hibernate.MappingException
import org.hibernate.dialect.identity.IdentityColumnSupportImpl

class SQLiteIdentityColumnSupport : IdentityColumnSupportImpl() {

    override fun supportsIdentityColumns(): Boolean {
        return true
    }

    @Throws(MappingException::class)
    override fun getIdentitySelectString(table: String?, column: String?, type: Int): String {
        return "select last_insert_rowid()"
    }

    @Throws(MappingException::class)
    override fun getIdentityColumnString(type: Int): String {
        return "integer"
    }
}

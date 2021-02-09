package com.example.bankingapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.bankingapp.ThreadExecutor

@Database(entities = [User::class,Transaction::class],version = 1)
@TypeConverters(DateConverter::class)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao() : UserDao

    abstract fun transactionDao() : TransactionDao

    companion object {
        private var instance: UserDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): UserDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, UserDatabase::class.java,
                    "user_database"
                ).fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()

            return instance!!
        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase(instance!!)
            }
        }
//Dummy data for 10 users having initial balance Rs 5000.00
        private fun populateDatabase(db: UserDatabase) {
            val userDao = db.userDao()
            ThreadExecutor.getInstance()?.diskIO?.execute(Runnable {
                kotlin.run {
                userDao.insertUser(User("Muskan Goel","muskangoel@gmail.com",1366424701,9000.00,"M32B3ZC",9742519023))
                userDao.insertUser(User("Saakshi Agarwal","saakshiagarwal@gmail.com",1234556840,12000.00,"S62E34F",7652981235))
                userDao.insertUser(User("Neha Jain","nehajain@gmail.com",6738995701,10000.00,"N25H34I",8890591236))
                userDao.insertUser(User("Ishita Beriwal","ishitaberiwal@gmail.com",4672995740,8000.00,"I32K34L",9245681237))
                userDao.insertUser(User("Brizish Safaq","brizishsafaq@gmail.com",1135974812,5000.00,"B35N67O",7789491238))
                userDao.insertUser(User("Shrikant Dalmia","shrikantdalmia@gmail.com",3254520958,7000.00,"S75Q67R",8543271239))
                userDao.insertUser(User("Amit Agarwal","amitagarwal@gmail.com",1357892321,8000.00,"A55T67U",8987681230))
                userDao.insertUser(User("Shishir Choudhary","shishirchoudhary@gmail.com",1372074867,10000.00,"S58W90X",8987491231))
                userDao.insertUser(User("Sneha Gupta","snehagupta@gmail.com",16780264876,9000.00,"S19Z90A",9876371232))
                userDao.insertUser(User("Rani Patra","ranipatra@gmail.com",1561092384,6000.00,"R88Z90B",77865491233))}
            })
        }
    }
}
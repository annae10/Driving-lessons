package com.harman.drivinglessons

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.harman.drivinglessons.fragments.list.ListFragment
import com.harman.drivinglessons.news.NewsActivity
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_tasks.*

class ProfileActivity : AppCompatActivity() , Communicator {

    var textView: TextView? = null

    var sharedPreferences: SharedPreferences? = null
    private val SHARED_PREF_NAME = "mypref"
    private val KEY_NAME = "name"
    private val KEY_EMAIL = "email"
    private val KEY_PASSWORD = "password"

    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var listener: NavController.OnDestinationChangedListener

    lateinit var toogle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.flFragment) as NavHostFragment
        navController = navHostFragment.navController

        sharedPreferences = getSharedPreferences("SHARED_PREF_NAME", Context.MODE_PRIVATE)


        val name_ = sharedPreferences?.getString("KEY_NAME", "")
        val email_ = sharedPreferences?.getString("KEY_EMAIL", "")

        val firstFragment = BlankFragment()
        val newsFragment = NewsFragment()
        val tasksFragment = TasksFragment()
        val notesFragment = ListFragment()

        val fragmentHome = BlankFragment()

        /*********************NAVIGATION DRAVER*****************/
        toogle = ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close)
        drawer_layout.addDrawerListener(toogle)
        toogle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.miItem1 -> {
                    drawer_layout.close()
                    Toast.makeText(applicationContext, "Clicked Item 1", Toast.LENGTH_SHORT).show()
                    setCurrentFragment(firstFragment)
                }

                R.id.miItem2 -> {
                    drawer_layout.close()
                    Toast.makeText(applicationContext, "Clicked Item 2", Toast.LENGTH_SHORT).show()
                    setCurrentFragment(newsFragment)
                }

                R.id.miItem3 -> {
                    drawer_layout.close()
                    setCurrentFragment(tasksFragment)
                    Toast.makeText(applicationContext, "Clicked Item 3", Toast.LENGTH_SHORT).show()
                }

                R.id.miItem4 -> {
                    drawer_layout.close()
                    setCurrentFragment(tasksFragment)
                    Toast.makeText(applicationContext, "Clicked Activity", Toast.LENGTH_SHORT)
                        .show()

                    val intent = Intent(this, NotesActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.miItem5 -> {
                    val editor = sharedPreferences!!.edit()
                    editor.clear()
                    editor.commit()
                    finish()
                    Toast.makeText(this@ProfileActivity, "Log out successfully", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }

                R.id.miItem6 -> {
                    drawer_layout.close()
                    setCurrentFragment(tasksFragment)
                    Toast.makeText(applicationContext, "Clicked Activity", Toast.LENGTH_SHORT)
                        .show()

                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.miItem7 -> {
                    drawer_layout.close()
                    Toast.makeText(applicationContext, "Clicked Activity", Toast.LENGTH_SHORT)
                        .show()

                    val intent = Intent(this, NewsActivity::class.java)
                    startActivity(intent)
                    true
                }
            }
            true


        }
        /************************/

    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

    override fun passDataCom(editTextInput: String) {
        TODO("Not yet implemented")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuLogout -> {
        val editor = sharedPreferences!!.edit()
        editor.clear()
        editor.commit()
        finish()
        Toast.makeText(this@ProfileActivity, "Log out successfully", Toast.LENGTH_SHORT).show()
        finish()
    }
    R.id.menuSettings -> Toast.makeText(this, "You clicked settings", Toast.LENGTH_LONG)
    .show()
}

if (toogle.onOptionsItemSelected(item)){
    return true
}

return true
}

}

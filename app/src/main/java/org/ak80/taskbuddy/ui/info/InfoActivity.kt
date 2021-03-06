package org.ak80.taskbuddy.ui.info

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.NavUtils
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import dagger.Lazy
import dagger.android.support.DaggerAppCompatActivity
import org.ak80.taskbuddy.R
import org.ak80.taskbuddy.ui.missions.MissionsActivity
import org.ak80.taskbuddy.ui.util.ActivityUtils
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import javax.inject.Inject


/**
 * View for showing the info screen
 */
class InfoActivity : DaggerAppCompatActivity(), AnkoLogger {

    @Inject
    lateinit var infoPresenter: InfoContract.Presenter
    @Inject
    lateinit var infoFragmentProvider: Lazy<InfoFragment>
    @Inject
    lateinit var fragment: InfoFragment

    private var drawerLayout: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_act)

        setupToolbar()
        setupNavDrawer()
        setupFragment()

        if (savedInstanceState != null) {
            info("${this.localClassName} instance state was saved")
        }
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setTitle(R.string.title_list_info)
        ab.setHomeAsUpIndicator(R.drawable.ic_menu)
        ab.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupNavDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout?.setStatusBarBackground(R.color.colorPrimaryDark)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        if (navigationView != null) {
            setupDrawerContent(navigationView)
        }
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_menu_item_missions -> {
                    startActivity(intentFor<MissionsActivity>())
                }
                R.id.navigation_menu_item_tasks -> {
                    NavUtils.navigateUpFromSameTask(this@InfoActivity)
                }
                R.id.navigation_menu_item_info -> {
                    // do nothing, we're already on that screen
                }
                else -> {
                    // ignore
                }
            }
            menuItem.isChecked = true
            drawerLayout?.closeDrawers()
            true
        }
    }

    private fun setupFragment() {
        ActivityUtils.loadFragment(R.id.contentFrame, infoFragmentProvider, supportFragmentManager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                openNavigationDrawer()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openNavigationDrawer() {
        drawerLayout?.openDrawer(GravityCompat.START)
    }
}
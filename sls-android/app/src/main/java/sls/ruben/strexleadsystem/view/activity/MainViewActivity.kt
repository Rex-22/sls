package sls.ruben.strexleadsystem.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main_view.*
import kotlinx.android.synthetic.main.app_bar_main_view.*
import sls.ruben.strexleadsystem.R
import sls.ruben.strexleadsystem.prefService
import sls.ruben.strexleadsystem.view.fragment.LeadFragment
import sls.ruben.strexleadsystem.view.fragment.dummy.DummyContent
import sls.ruben.strexleadsystem.viewModel.LoginViewModel

class MainViewActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, LeadFragment.OnListFragmentInteractionListener {

    // This is BAD!!!
    var showAll = false
    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view)
        setSupportActionBar(toolbar)

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        if (prefService.noServerConnection) {
            Snackbar.make(findViewById(R.id.fab), getString(R.string.error_no_server_connection), Snackbar.LENGTH_LONG)
                    .show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_view, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_view -> {
                showAll = !showAll
                Snackbar.make(fab,
                        if (showAll) "Showing all leads" else "Showing my leads",
                        Snackbar.LENGTH_LONG).setAction("Action", null).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_lead -> {

            }
            R.id.nav_company -> {

            }
            R.id.nav_report -> {

            }
            R.id.nav_setting -> {

            }
            R.id.nav_logout -> {
                loginViewModel.logout()
                gotoLogin()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun gotoLogin() {
        startActivity(Intent(this, LoginViewActivity::class.java))
        finish()
    }

    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
        Snackbar.make(fab, item!!.details, Snackbar.LENGTH_SHORT).setAction("Action", null).show()
    }

}

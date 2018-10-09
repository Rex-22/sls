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
import sls.ruben.strexleadsystem.model.CompanyModel
import sls.ruben.strexleadsystem.model.LeadModel
import sls.ruben.strexleadsystem.view.fragment.CompanyFragment
import sls.ruben.strexleadsystem.view.fragment.LeadFragment
import sls.ruben.strexleadsystem.viewModel.LoginViewModel

class MainViewActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, LeadFragment.OnListFragmentInteractionListener, CompanyFragment.OnListFragmentInteractionListener {

    // This is BAD!!!
    var showAll = false
    lateinit var viewModel: LoginViewModel
    private val leadFragment = LeadFragment()
    private val companyFragment = CompanyFragment()

    private val TAG_LEAD = "FRAGMENT_LEAD"
    private val TAG_COMPANY = "FRAGMENT_LEAD"
    private val TAG_LEAD_EDIT = "FRAGMENT_LEAD_EDIT"
    private val TAG_COMPANY_EDIT = "FRAGMENT_COMPANY_EDIT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.fragment, leadFragment, TAG_LEAD)
            fragmentTransaction.commit()
        }
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
            R.id.menu_refresh -> {
                for (fragment in supportFragmentManager.fragments) {
                    if (fragment != null && fragment.isVisible)
                        fragment.onOptionsItemSelected(item)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_lead -> {
                changeFragment(TAG_LEAD)
            }
            R.id.nav_company -> {
                changeFragment(TAG_COMPANY)
            }
            R.id.nav_report -> {

            }
            R.id.nav_setting -> {

            }
            R.id.nav_logout -> {
                viewModel.logout()
                gotoLogin()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun changeFragment(tag: String) {
        val fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment, companyFragment, tag)
            fragmentTransaction.commit()
        }
    }

    private fun gotoLogin() {
        startActivity(Intent(this, LoginViewActivity::class.java))
        finish()
    }

    override fun onListFragmentInteraction(item: LeadModel?) {
        Snackbar.make(fab, "${item!!.firstname} ${item.lastname}", Snackbar.LENGTH_SHORT).setAction("Action", null).show()
    }

    override fun onListFragmentInteraction(item: CompanyModel?) {
        Snackbar.make(fab, item!!.name!!, Snackbar.LENGTH_SHORT).setAction("Action", null).show()
    }

}

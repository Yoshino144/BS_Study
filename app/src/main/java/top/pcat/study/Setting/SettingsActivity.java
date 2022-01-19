package top.pcat.study.Setting;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceGroupAdapter;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.RecyclerView;

import top.pcat.study.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            findPreference("yejian").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Toast.makeText(getActivity(), "Preference 被点击了"+preference, Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

        }

        private void setAllPreferencesToAvoidHavingExtraSpace(Preference preference) {
            preference.setIconSpaceReserved(false);
            if (preference instanceof PreferenceGroup)
                for(int i=0;i<((PreferenceGroup) preference).getPreferenceCount();i++){
                    setAllPreferencesToAvoidHavingExtraSpace(((PreferenceGroup) preference).getPreference(i));
                }
        }

        @Override
        public void  setPreferenceScreen(PreferenceScreen preferenceScreen) {
            if (preferenceScreen != null)
                setAllPreferencesToAvoidHavingExtraSpace(preferenceScreen);
            super.setPreferenceScreen(preferenceScreen);

        }

        @Override
        @SuppressLint("RestrictedApi")
        protected RecyclerView.Adapter onCreateAdapter(PreferenceScreen preferenceScreen) {
            return new PreferenceGroupAdapter(preferenceScreen){
                @SuppressLint("RestrictedApi")
                @Override
                public void onPreferenceHierarchyChange(Preference preference) {
                    if(null!=preference){
                        setAllPreferencesToAvoidHavingExtraSpace(preference);
                    }
                    super.onPreferenceHierarchyChange(preference);
                }
            };
        }
    }
}
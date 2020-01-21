package idv.ron.contactdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private ListView lvContacts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        lvContacts = (ListView) findViewById(R.id.lvContact);
        askPermissions();
    }

    private void showContacts() {
        lvContacts.setAdapter(getContactListAdapter());
        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                String text = "";
                String name = ((TextView) view.findViewById(R.id.tvName)).getText().toString();
                text += name;
                Cursor phones = getPhones(id);
                if (phones.getCount() <= 0) {
                    text += "\n" + getString(R.string.msg_PhoneNoNotFound);
                    showToast(text);
                    return;
                }

                while (phones.moveToNext()) {
                    int phoneTypeID = phones.getInt(
                            phones.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.TYPE));
                    String type = getString(
                            ContactsContract.CommonDataKinds.Phone.
                                    getTypeLabelResource(phoneTypeID));
                    String phoneNo = phones.getString(phones.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER));
                    text += "\n" + type + "：" + phoneNo;
                }
                showToast(text);
            }
        });
    }

    private ListAdapter getContactListAdapter() {
        Cursor cursor = getContacts();
        String[] columns = {
                ContactsContract.Contacts.DISPLAY_NAME
        };

        int[] textViewIds = {
                R.id.tvName
        };

        return new SimpleCursorAdapter(
                this, R.layout.contact_listview_item,
                cursor, columns, textViewIds,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    private Cursor getContacts() {
        Uri contactsUri = ContactsContract.Contacts.CONTENT_URI;
        String[] columns = {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };

        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME;
        return getContentResolver().query(
                contactsUri, columns, null, null, sortOrder);
    }

    private Cursor getPhones(long id) {
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] columns = {
                ContactsContract.CommonDataKinds.Phone.TYPE,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(id)};
        return getContentResolver().query(
                phoneUri, columns, selection, selectionArgs, null);
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private final static int REQ_PERMISSIONS = 0;

    private void askPermissions() {
        String[] permissions = {
                Manifest.permission.READ_CONTACTS
        };

        Set<String> permissionsRequest = new HashSet<>();
        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionsRequest.add(permission);
            }
        }

        if (permissionsRequest.isEmpty()) {
            showContacts();
        } else {
            ActivityCompat.requestPermissions(this,
                    permissionsRequest.toArray(new String[permissionsRequest.size()]),
                    REQ_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQ_PERMISSIONS:
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        String text = getString(R.string.text_ShouldGrant);
                        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
                showContacts();
                break;
        }
    }
}

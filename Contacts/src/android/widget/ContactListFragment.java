package android.widget;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.widget.SimpleCursorAdapter;

import com.example.contacts.R;
import com.example.contacts.R.layout;

public class ContactListFragment extends ListFragment 
    implements LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter mAdapter;
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
     
        super.onActivityCreated(savedInstanceState);
        
        setEmptyText("Woot? Nothing?");
        
        //setHasOptionsMenu(true);
        
        mAdapter = new SimpleCursorAdapter(getActivity(), 
                R.layout.list_item, null, 
                new String[] { "from" }, 
                new int[] { android.R.id.text1 }, 
                0);
        
        setListShown(false);
        
        getLoaderManager().initLoader(0, null, this);
    }

    
    // Interface 
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {

        return new CursorLoader(getActivity(), 
                Contacts.CONTENT_URI,
                new String[] { ContactsContract.Contacts.DISPLAY_NAME },
                null, null,
                ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");        
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mAdapter.swapCursor(data);

        // The list should now be shown.
        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }        
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {

        mAdapter.swapCursor(null);
        
    }
}
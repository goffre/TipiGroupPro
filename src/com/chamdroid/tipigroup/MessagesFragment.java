package com.chamdroid.tipigroup;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import adaptaters.ContactAdapter;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import objects.Contact;

public class MessagesFragment extends Fragment{
	private ListView lv;
	private ArrayList<Contact> contacts;
	private String leJson;
	private JSONArray arrayJson;

	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static MessagesFragment newInstance(int sectionNumber) {
		MessagesFragment fragment = new MessagesFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public MessagesFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);

		lv = (ListView)rootView.findViewById(R.id.listview);
		contacts = new ArrayList<Contact>();
		Bundle json = getArguments();
		leJson = json.getString("leJson");
		Log.i("dispJson", ""+leJson);
		
		try{
			JSONObject jsonContacts = new JSONObject(leJson);
			arrayJson = jsonContacts.getJSONArray("contacts");
			Log.i("arrayJson", ""+arrayJson);
			
			Contact currentContact = null;
			
			for (int i = 0; i < arrayJson.length(); i++) {
				currentContact = new Contact();

				JSONObject jsonObject = arrayJson.getJSONObject(i);
				
					
				String name = jsonObject.getString("name");
				String phone = jsonObject.getString("phone");

				currentContact.setName(name);
				currentContact.setPhone(phone);
				contacts.add(currentContact);
			}
			ArrayAdapter<Contact> adapter = new ContactAdapter(getActivity(), R.layout.row, contacts);
			lv.setAdapter(adapter);
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Intent intent = new Intent(getActivity(), SendSMS.class);
					intent.putExtra("contact", contacts.get(position));
					startActivity(intent);
				}
			});
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
	}
}

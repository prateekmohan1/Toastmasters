package com.example.newprateek;

import java.io.BufferedReader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.mongodb.*;
import com.mongodb.gridfs.*;
import com.mongodb.io.*;
import com.mongodb.tools.*;
import com.mongodb.util.*;
import com.mongodb.util.management.*;
import com.mongodb.util.management.jmx.*;

public class NewsFragment extends Fragment {	
	
	RelativeLayout relLayout;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
    	View rootView = inflater.inflate(R.layout.fragment_newsapp, container, false);
    	relLayout = (RelativeLayout) rootView.findViewById(R.id.rel_layout);
    	
        final String url = "https://api.mongolab.com/api/1/databases/testprateek/collections/new_collection?q={\"active\": \"Paula Petersen\"}apiKey=13z0kWZhKpuCAHvDCcRYx_H0EacWTnAc";
        //q={"active": true}
        String response = null;
        
        final sendGet sendReq = new sendGet(rootView);
        
        Button retrieveNames = new Button(getActivity());
        retrieveNames.setText("Retrieve Names");
        retrieveNames.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stubss
				sendReq.execute(url);
				
			}
		});
        retrieveNames.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        relLayout.addView(retrieveNames);
        
        
        /*ToggleButton btn1 = new ToggleButton(getActivity());sss
        btn1.setTextOn("Toggle On");
        btn1.setTextOff("Toggle Off");
        btn1.setChecked(true);
        btn1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        
        relLayout.addView(btn1);*/
        
        //sendGet get_statement = new sendGet();
        //get_statement.delegate = this;
        
        //new sendGet().execute(url);
        
        //Log.i("TEST ASDF ASDF", response);
        return rootView;
    }
 
   public class sendGet extends AsyncTask<String, Integer, String> {
	   
	   ProgressDialog progressDialog = null;
	   private View view;
	   
	   public sendGet(View rootView) {
		// TODO Auto-generated constructor stub
		   view = rootView;
	}

	@Override
	   protected void onPreExecute(){
		   super.onPreExecute();
		   progressDialog = ProgressDialog.show(getActivity(), "Wait", "Downloading...");
	   }
	   
	   @Override
	   protected String doInBackground(String... params) {
		   // TODO Auto-generated method stub
		   
		   /*
		   Log.i("TEST TEST", "HERE 4");
		   
		   MongoClient mongoClient = null;
		   MongoClientURI uri = new MongoClientURI("mongodb://ds061258.mongolab.com:61258/testprateek");
		   
		   Log.i("TEST TEST", "HERE 2");
		   
		   try {
			mongoClient = new MongoClient(uri);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		   Log.i("TEST TEST", "HERE 5");
		   
		   DB db = mongoClient.getDB(uri.getDatabase());
		   
		   Set<String> colls = db.getCollectionNames();
		   
		   for (String s:colls) {
			   System.out.println(s);
		   }
		   
		   Log.i("TEST TEST", "HERE 3");
		return null;*/
		   
		   HttpClient httpclient = new DefaultHttpClient();
		   HttpResponse response = null;
		   try {
			   Log.i("TEST TEST", "HERE 1");
			   response = httpclient.execute(new HttpGet(
					   "https://api.mongolab.com/api/1/databases/testprateek/collections/new_collection?apiKey=13z0kWZhKpuCAHvDCcRYx_H0EacWTnAc"
					   //"https://api.mongolab.com/api/1/databases/testprateek/collections/new_collection?q=" +
					   //URLEncoder.encode("{\"name\": \"Paula Petersen\"}", "UTF-8") +
					   //"&apiKey=13z0kWZhKpuCAHvDCcRYx_H0EacWTnAc"
					   ));
			   Log.i("TEST TEST", "HERE 2");
		   } catch (ClientProtocolException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   } catch (IOException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   }
			
		   StatusLine statusLine = response.getStatusLine();
		   //Log.i("TEST TEST", String.valueOf(statusLine.getStatusCode()));
	       if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
	    	   ByteArrayOutputStream out = new ByteArrayOutputStream();
	    	   try {
	    		   response.getEntity().writeTo(out);
	    	   } catch (IOException e) {
	    		   // TODO Auto-generated catch block
	    		   e.printStackTrace();
	    	   }
	    	   try {
	    		   out.close();
	    	   } catch (IOException e) {
	    		   // TODO Auto-generated catch block
	    		   e.printStackTrace();
	    	   }
	    	   String responseString = out.toString();
	    	   //Log.i("TEST TEST", responseString);
	    	   return responseString;
	        }
	        else {
	        	try {
	        		response.getEntity().getContent().close();
				} catch (IllegalStateException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	return null;
	        }
		}
		
		@Override
		protected void onPostExecute(String result) {
			//delegate.processFinish(result);
			
			relLayout = (RelativeLayout) view.findViewById(R.id.rel_layout);
			progressDialog.dismiss();
			
			JSONArray toastmasterMembers = null;
			JSONObject memberName = new JSONObject();
			
			try {
				toastmasterMembers = new JSONArray(result);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (int i=0; i< toastmasterMembers.length(); i++) {
				
				try {
					memberName = toastmasterMembers.getJSONObject(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				TextView txtView = new TextView(getActivity());
				txtView.setId(i);
				
				try {
					txtView.setText(memberName.getString("name"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				txtView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				relLayout.addView(txtView);
			}
			
			/*JSONObject jsonObj = null; 
			
			try {
				jsonObj = new JSONObject(result);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			TextView txtView = new TextView(getActivity());
			
			try {
				txtView.setText(jsonObj.toString(1));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			txtView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			relLayout.addView(txtView);*/
		}
	}
}

package com.example.sinbarrerasudb.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sinbarrerasudb.R;
import com.example.sinbarrerasudb.clases.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Consulta.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Consulta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Consulta extends Fragment
implements Response.Listener<JSONObject>,Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    EditText campoDocumento;
    TextView txtNombre,txtProfesion;
    ImageButton btnConsultarUsuario;
    ProgressDialog progreso;

    RequestQueue resquest;
    JsonObjectRequest jsonObjectRequest;

    public Consulta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Consulta.
     */
    // TODO: Rename and change types and number of parameters
    public static Consulta newInstance(String param1, String param2) {
        Consulta fragment = new Consulta();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResponse(JSONObject response) {
    progreso.hide();
    Toast.makeText(getContext(),"Mensaje: "+response,Toast.LENGTH_SHORT).show();

        Usuario miUsuario= new Usuario();
        JSONArray json=response.optJSONArray("usuario");
        JSONObject jsonObject=null;

        try {
            jsonObject= json.getJSONObject(0);
            miUsuario.setNombre(jsonObject.optString("nombre"));
            miUsuario.setProfesion(jsonObject.optString("profesion"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        txtNombre.setText(miUsuario.getNombre());
        txtProfesion.setText(miUsuario.getProfesion());
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(),"No se consulto",Toast.LENGTH_SHORT).show();
        Log.i("Error",error.toString());
    }



    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista=inflater.inflate(R.layout.fragment_consulta, container, false);

        campoDocumento= vista.findViewById(R.id.campoDocumento);
        txtNombre= vista.findViewById(R.id.txtNombre);
        txtProfesion= vista.findViewById(R.id.txtProfesion);
        btnConsultarUsuario=  vista.findViewById(R.id.btnConsultarUsuario);

        resquest= Volley.newRequestQueue(getContext());

        btnConsultarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });

        return vista;
    }

    private void cargarWebService() {

        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Consultando...");
        progreso.show();
        String url="http://192.168.1.3/ejemploBDremota/wsJSONConsultarUsuario.php?documento="
                +campoDocumento.getText().toString();

        jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        resquest.add(jsonObjectRequest);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

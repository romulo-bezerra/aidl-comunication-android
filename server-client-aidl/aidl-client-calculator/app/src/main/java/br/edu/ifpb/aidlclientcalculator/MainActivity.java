package br.edu.ifpb.aidlclientcalculator;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import br.edu.ifpb.aidlservercalculator.AIDLPrincipal;

public class MainActivity extends AppCompatActivity {

    private AIDLPrincipal aidlPrincipal;
    private ServiceConnection serviceConnection;
    private EditText etValorA, etValorB;
    private TextView tvResultado;

    public void onClickSoma(View view) throws RemoteException {
        tvResultado.setText(String.valueOf(executeOperations(etValorA, etValorB, OperationEnum.SOMAR)));
    }

    public void onClickSubtrai(View view) throws RemoteException {
        tvResultado.setText(String.valueOf(executeOperations(etValorA, etValorB, OperationEnum.SUBTRAIR)));
    }

    public void onClickMultiplica(View view) throws RemoteException {
        tvResultado.setText(String.valueOf(executeOperations(etValorA, etValorB, OperationEnum.MULTIPLICAR)));
    }

    public void onClickDivide(View view) throws RemoteException {
        tvResultado.setText(String.valueOf(executeOperations(etValorA, etValorB, OperationEnum.DIVIDIR)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etValorA = findViewById(R.id.etValorA);
        etValorB = findViewById(R.id.etValorB);
        tvResultado = findViewById(R.id.tvResultado);

        //Service Connection for comunication App Server
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i("TAG", "onServiceConnected");
                aidlPrincipal = AIDLPrincipal.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i("TAG", "onServiceDisconnected");
                aidlPrincipal = null;
            }
        };

        //Intent initialize service
        Intent intent = new Intent("br.edu.ifpb.aidlservercalculator.CALCULATE");
        intent.setPackage("br.edu.ifpb.aidlservercalculator");
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);

    }

    private double executeOperations(EditText etValueA, EditText etValueB, OperationEnum operacao) throws RemoteException {

        double a = Double.parseDouble(etValorA.getText().toString());
        double b = Double.parseDouble(etValorB.getText().toString());

        if (operacao.equals(OperationEnum.SOMAR)){
            return aidlPrincipal.somar(a,b);
        } else if (operacao.equals(OperationEnum.SUBTRAIR)){
            return aidlPrincipal.subtrair(a,b);
        } else if (operacao.equals(OperationEnum.MULTIPLICAR)){
            return aidlPrincipal.multiplicar(a,b);
        } else {
            return aidlPrincipal.dividir(a,b);
        }
    }

}
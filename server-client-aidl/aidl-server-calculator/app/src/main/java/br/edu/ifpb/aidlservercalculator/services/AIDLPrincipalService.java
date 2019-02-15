package br.edu.ifpb.aidlservercalculator.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import br.edu.ifpb.aidlservercalculator.AIDLPrincipal;

public class AIDLPrincipalService extends Service {

    private AIDLPrincipalImpl aidlPrincipal;

    public AIDLPrincipalService(){
        aidlPrincipal = new AIDLPrincipalImpl();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return aidlPrincipal;
    }

    public class AIDLPrincipalImpl extends AIDLPrincipal.Stub{

        @Override
        public double somar(double a, double b) throws RemoteException {
            return (a + b);
        }

        @Override
        public double subtrair(double a, double b) throws RemoteException {
            return (a - b);
        }

        @Override
        public double multiplicar(double a, double b) throws RemoteException {
            return (a * b);
        }

        @Override
        public double dividir(double a, double b) throws RemoteException {
            return (a / b);
        }

    }
}

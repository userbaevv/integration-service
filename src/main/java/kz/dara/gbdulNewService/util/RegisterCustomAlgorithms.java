package kz.dara.gbdulNewService.util;

import kz.gov.pki.kalkan.xmldsig.SignatureGostR3410_2015;
import org.apache.xml.security.Init;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.algorithms.JCEMapper.Algorithm;

public class RegisterCustomAlgorithms {
    static {
        // Регистрация нового алгоритма подписи
        try {
            JCEMapper.register(
                    SignatureGostR3410_2015.GostR34102015GostR34112015_512._URI,
                    new Algorithm(
                            SignatureGostR3410_2015.GostR34102015GostR34112015_512.class.getName(),
                            null,
                            null
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void register() {
        // Инициализация Apache XML Security
        Init.init();
    }

    public static void main(String[] args) {
        // Вызов метода регистрации
        register();
    }
}

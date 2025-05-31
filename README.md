Програма до курсової роботи

Адреса пошти та код доступу для логеру задається env змінними LOG_EMAIL_USER і LOG_EMAIL_PASS

module-info інколи не знаходить:
    java.mail;
    org.junit.jupiter.api;
    org.junit.jupiter.engine;
    org.junit.platform.commons;
    org.junit.platform.launcher;

В такому випадку необхідно вручну видалити(Якщо IDE їх бачить) і встановити Libraries:
    javax.mail:javax.mail-api:1.6.2
    org.junit.jupiter:junit-jupiter:5.12.2
    org.junit.platform:junit-platform-launcher:1.8.2
    com.sun.mail:javax.mail:1.6.2

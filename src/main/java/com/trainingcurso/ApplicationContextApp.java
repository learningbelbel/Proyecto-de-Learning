package com.trainingcurso;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextApp implements ApplicationContextAware {

    /*
     * Consiste en un fichero donde se añadirán
     * todos los objectos que deberán existir en la
     * aplicación al inicializarse la misma.
     */
    private static ApplicationContext CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //with this i Just set up the context to my variable
        CONTEXT = applicationContext;
    }
    
    //I have to return an Object, Bean that returns the Context
    //WITH This I access to the Beans in the application, every BEAN = Object
    public static Object getBean(String beanName){
        return CONTEXT.getBean(beanName);
    }


    //IMPORTANT
    //we have to create the BEAN and the principal class "Application" as was created BCryptpasswordEncoder Bean

}

package com.taiwanlife.ikash.backend.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

import com.taiwanlife.ikash.backend.configuration.DatasourceContext;
import com.taiwanlife.ikash.backend.configuration.DatasourceScope;

public class DynamicDatasourceAspect {
    @Pointcut("@annotation(dataSourceScope)")
    public void dynamicPointcut(DatasourceScope dataSourceScope){}

    @Around(value = "dynamicPointcut(dataSourceScope)", argNames = "joinPoint,dataSourceScope")
    public Object dynamicAround(ProceedingJoinPoint joinPoint , DatasourceScope dataSourceScope) throws Throwable {
        String scope = dataSourceScope.scope();
        DatasourceContext.setDatasource(scope);
        return joinPoint.proceed();
    }

}

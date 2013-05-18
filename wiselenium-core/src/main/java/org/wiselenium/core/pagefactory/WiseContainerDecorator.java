package org.wiselenium.core.pagefactory;

import static org.wiselenium.core.pagefactory.AnnotationUtils.isAnnotationPresent;
import static org.wiselenium.core.pagefactory.ClasspathUtils.findImplementationClass;

import java.lang.reflect.Field;

import net.sf.cglib.proxy.Enhancer;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

/**
 * Class responsible for decorating WebElements into Containers.
 * 
 * @author Andre Ricardo Schaffer
 * @since 0.0.1
 */
class WiseContainerDecorator extends DefaultFieldDecorator implements WiseDecoratorChain {
	
	private ExtendedFieldDecorator nextDecoratorInChain;
	
	
	WiseContainerDecorator(ElementLocatorFactory factory) {
		super(factory);
	}
	
	private static Enhancer createEnhancer(WebElement webElement, Class<?> implentationClass) {
		Enhancer e = new Enhancer();
		e.setSuperclass(implentationClass);
		e.setInterfaces(new Class[] { WrapsElement.class });
		e.setCallback(WiseElementProxy.getInstance(webElement));
		
		return e;
	}
	
	private static Object createInstanceWithEmptyConstructor(WebElement webElement,
		Class<?> implentationClass) {
		Enhancer e = createEnhancer(webElement, implentationClass);
		return e.create();
	}
	
	private static Object createInstanceWithWebElementConstructor(WebElement webElement,
		Class<?> implentationClass) {
		
		try {
			return implentationClass.getConstructor(WebElement.class).newInstance(webElement);
		} catch (Exception e) {
			throw new ClassWithoutConstructorWithWebElementException(e);
		}
	}
	
	private static boolean shouldDecorate(Class<?> clazz) {
		return isAnnotationPresent(clazz, org.wiselenium.core.Container.class);
	}
	
	private static boolean shouldDecorate(Field field) {
		return shouldDecorate(field.getType());
	}
	
	@Override
	public Object decorate(Class<?> clazz, WebElement webElement) {
		// TODO decorate lists of containers as well
		if (!shouldDecorate(clazz)) return this.callNextDecorator(clazz, webElement);
		
		Class<?> implentationClass = findImplementationClass(clazz);
		Object instance;
		try {
			instance = createInstanceWithWebElementConstructor(webElement, implentationClass);
		} catch (ClassWithoutConstructorWithWebElementException e) {
			instance = createInstanceWithEmptyConstructor(webElement, implentationClass);
		}
		// TODO init container elements as well
		return instance;
	}
	
	@Override
	public Object decorate(ClassLoader loader, Field field) {
		// TODO decorate lists of containers as well
		if (!shouldDecorate(field)) return this.callNextDecorator(loader, field);
		
		ElementLocator locator = this.factory.createLocator(field);
		if (locator == null) return null;
		
		WebElement webElement = this.proxyForLocator(loader, locator);
		return this.decorate(field.getType(), webElement);
	}
	
	@Override
	public WiseDecoratorChain setNext(ExtendedFieldDecorator decorator) {
		this.nextDecoratorInChain = decorator;
		return this;
	}
	
	private Object callNextDecorator(Class<?> clazz, WebElement webElement) {
		if (this.nextDecoratorInChain != null)
			return this.nextDecoratorInChain.decorate(clazz, webElement);
		return null;
	}
	
	private Object callNextDecorator(ClassLoader loader, Field field) {
		if (this.nextDecoratorInChain != null)
			return this.nextDecoratorInChain.decorate(loader, field);
		return null;
	}
	
}
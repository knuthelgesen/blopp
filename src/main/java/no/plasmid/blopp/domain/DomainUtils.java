package no.plasmid.blopp.domain;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import com.tinkerpop.blueprints.impls.orient.OrientEdge;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

public class DomainUtils {

	private final static Logger LOG = LoggerFactory.getLogger(DomainUtils.class);

	private static Map<String, Class<? extends DomainObject<?>>> domainObjectClasses;
	private static Map<Class<? extends DomainObject<?>>, String> vertexClasses;
	
	private static Map<String, Class<? extends DomainRelation<?>>> domainRelationClasses;
	private static Map<Class<? extends DomainRelation<?>>, String> edgeClasses;
	
	static {
		domainObjectClasses = new HashMap<String, Class<? extends DomainObject<?>>>();
		vertexClasses = new HashMap<Class<? extends DomainObject<?>>, String>();
		domainRelationClasses = new HashMap<String, Class<? extends DomainRelation<?>>>();
		edgeClasses = new HashMap<Class<? extends DomainRelation<?>>, String>();
	
		loadClassInfo();
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends DomainObject<?>> T createDomainObjectInstance(OrientVertex ov) {
		T rc = null;
		Class<? extends DomainObject<?>> domainObjectClass = resolveDomainObjectClassName(ov.getType().getName());
	
		try {
			rc = (T)domainObjectClass.getConstructor(OrientVertex.class).newInstance(ov);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			LOG.error(e.getMessage(), e);
		}
		
		return rc;
	}

	@SuppressWarnings("unchecked")
	public static <T extends DomainRelation<?>> T createDomainRelationInstance(OrientEdge oe) {
		T rc = null;
		Class<? extends DomainRelation<?>> domainRelationClass = resolveDomainRelationClassName(oe.getType().getName());
		
		try {
			rc = (T)domainRelationClass.getConstructor(OrientEdge.class).newInstance(oe);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			LOG.error(e.getMessage(), e);
		}
		
		return rc;
	}
	
	public static Class<? extends DomainObject<?>> resolveDomainObjectClassName(String graphClassName) {
		return domainObjectClasses.get(graphClassName);
	}

	public static String resolveVertexClassName(Class<? extends DomainObject<?>> domainObjectClass) {
		return vertexClasses.get(domainObjectClass);
	}

	public static Class<? extends DomainRelation<?>> resolveDomainRelationClassName(String graphClassName) {
		return domainRelationClasses.get(graphClassName);
	}

	public static String resolveEdgeClassName(Class<? extends DomainRelation<?>> domainRelationClass) {
		return edgeClasses.get(domainRelationClass);
	}

	private static void loadClassInfo() {
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(VertexClassName.class));
		for (BeanDefinition bd : scanner.findCandidateComponents("no.plasmid.blopp")) {
			try {
				@SuppressWarnings("unchecked")
				Class<? extends DomainObject<?>> clazz = (Class<? extends DomainObject<?>>) Class.forName(bd.getBeanClassName());
				VertexClassName vertexClassName = clazz.getAnnotation(VertexClassName.class);
				domainObjectClasses.put(vertexClassName.value(), clazz);
				vertexClasses.put(clazz, vertexClassName.value());
			} catch (ClassNotFoundException e) {
				LOG.error(e.getMessage(), e);
			}
		}
		
		scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(EdgeClassName.class));
		for (BeanDefinition bd : scanner.findCandidateComponents("no.plasmid.blopp")) {
			try {
				@SuppressWarnings("unchecked")
				Class<? extends DomainRelation<?>> clazz = (Class<? extends DomainRelation<?>>) Class.forName(bd.getBeanClassName());
				EdgeClassName edgeClassName = clazz.getAnnotation(EdgeClassName.class);
				domainRelationClasses.put(edgeClassName.value(), clazz);
				edgeClasses.put(clazz, edgeClassName.value());
			} catch (ClassNotFoundException e) {
				LOG.error(e.getMessage(), e);
			}
		}
	}

}

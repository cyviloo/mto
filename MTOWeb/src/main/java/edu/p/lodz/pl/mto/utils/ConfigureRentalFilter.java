/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.utils;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.expressions.Expression;
import org.eclipse.persistence.expressions.ExpressionBuilder;
import org.eclipse.persistence.mappings.OneToManyMapping;
import org.eclipse.persistence.mappings.OneToOneMapping;

/**
 *
 * @author Tomasz
 */
public class ConfigureRentalFilter implements DescriptorCustomizer {
 
	
    @Override
    public void customize(ClassDescriptor descriptor) throws Exception {
OneToOneMapping mapping = (OneToOneMapping) descriptor
				.getMappingForAttributeName("rentalList");
 
		ExpressionBuilder eb = new ExpressionBuilder(mapping
				.getReferenceClass());
		Expression fkExp = eb.getField("id_book").equal(eb.getParameter("id_book"));
		Expression activeExp = eb.get("active").equal(true);
 
		mapping.setSelectionCriteria(fkExp.and(activeExp));    }
}
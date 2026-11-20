package com.star.Ba.jsf;

import com.star.Ba.model.TypeVoie;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.selectonemenu.SelectOneMenu;

/**
 * @author
 * 
 */
@FacesConverter(value ="typeVoieConverter")
public class TypeVoieConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {

		List list = null;

		System.out.println("from as ogject: " + component);
        SelectOneMenu menu = (SelectOneMenu) component;
        for (UIComponent comp : menu.getChildren()) {
            if (comp instanceof UISelectItems) {
                UISelectItems item = (UISelectItems) comp;
                if (item.getValue() instanceof List) {
                	System.out.println("from converter " + item);
                    //list = (List) item.getValue();
                }
            }
        }
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
		System.out.println("from as string " + modelValue);
		try{
			TypeVoie tv = (TypeVoie) modelValue; 
			if(tv != null){
				return "t" + String.valueOf(tv.getCode());
			}	
		}catch(Exception e){
		}
		
		return null; 
	}
	
}
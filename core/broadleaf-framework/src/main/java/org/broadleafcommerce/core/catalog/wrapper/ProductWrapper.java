/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.core.catalog.wrapper;

import org.broadleafcommerce.common.api.APIWrapper;
import org.broadleafcommerce.common.api.BaseWrapper;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.media.domain.Media;
import org.broadleafcommerce.core.media.wrapper.MediaWrapper;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is a JAXB wrapper around Product.
 *
 * User: Kelly Tisdell
 * Date: 4/10/12
 */
@XmlRootElement(name = "product")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ProductWrapper extends BaseWrapper implements APIWrapper<Product>{

    @XmlElement
    protected Long id;
    
    @XmlElement
    protected String name;

    @XmlElement
    protected String description;

    @XmlElement
    protected Date activeStartDate;

    @XmlElement
    protected Date activeEndDate;

    @XmlElement
    protected CategoryWrapper defaultCategory;

    @XmlElement
    protected String manufacturer;

    @XmlElement
    protected String model;

    @XmlElement
    protected String promoMessage;

    @XmlElementWrapper(name = "productMediaList")
    @XmlElement
    protected List<MediaWrapper> productMedia;

    @Override
    public void wrap(Product model) {
        this.id = model.getId();
        this.name = model.getName();
        this.description = model.getDescription();
        this.activeStartDate = model.getActiveStartDate();
        this.activeEndDate = model.getActiveEndDate();
        
        this.manufacturer = model.getManufacturer();
        this.model = model.getModel();
        this.promoMessage = model.getPromoMessage();
        if (model.getProductMedia() != null && model.getProductMedia().size() > 0) {
            productMedia = new ArrayList<MediaWrapper>();
            for (Media media : model.getProductMedia().values()) {
                MediaWrapper wrapper = (MediaWrapper)entityConfiguration.createEntityInstance(MediaWrapper.class.getName());
                wrapper.wrap(media);
                productMedia.add(wrapper);
            }
        }
        if (model.getDefaultCategory() != null) {
            defaultCategory = (CategoryWrapper)entityConfiguration.createEntityInstance(CategoryWrapper.class.getName());
            defaultCategory.wrap(model.getDefaultCategory());
        }
    }
}

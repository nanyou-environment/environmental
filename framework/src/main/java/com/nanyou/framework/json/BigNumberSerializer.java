package com.nanyou.framework.json;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;

public class BigNumberSerializer implements JSONSerializer {

	public void serialize(Object obj,Writer os) {
		try {
			os.write( obj.toString() );
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Object deseialize(Class paramType, String json) {
		if (paramType == BigDecimal.class)
        {
            return new BigDecimal(json.trim());
        }

        if (paramType == BigInteger.class)
        {
            return new BigInteger(json.trim());
        }
        
		return null;
	}

}

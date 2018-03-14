package com.oracle.mw.sc.haier.Utils;

import java.io.IOException;

import com.haier.coherence.entity.EAILogData;
import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;
import com.tangosol.util.InvocableMap.Entry;
import com.tangosol.util.processor.AbstractProcessor;

public class CoherenceObjectUpdate extends AbstractProcessor implements PortableObject{
	private EAILogData to_new;
	public CoherenceObjectUpdate(){}
	public CoherenceObjectUpdate(EAILogData to){
		to_new=to;
	}
	@Override
	public Object process(Entry entry) {
		EAILogData to_entry=(EAILogData)entry.getValue();
		entry.setValue(to_new);
		return entry;
	}

	@Override
	public void readExternal(PofReader reader) throws IOException {
		to_new=reader.readObject(0);
	}

	@Override
	public void writeExternal(PofWriter writer) throws IOException {
		writer.writeObject(0, to_new);
	}

}

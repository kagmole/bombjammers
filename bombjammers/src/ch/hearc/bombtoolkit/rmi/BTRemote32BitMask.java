package ch.hearc.bombtoolkit.rmi;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class BTRemote32BitMask extends BTAbstractRemoteBitMask
		implements Externalizable {

	private static final int MSB_MASK = 31;
	
	private int mask;
	
	public BTRemote32BitMask() {
		mask = 0;
	}

	@Override
	public void readExternal(ObjectInput input) throws IOException,
			ClassNotFoundException {
		mask = input.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeInt(mask);
	}
}

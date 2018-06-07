package game;
/**
 * Stack�� ���·� ����ġ�� �����ϴ� Ŭ����.
 */
public class Exp {
	private int[] data; // Stack �迭 ����.
	private int top; // Stack�� ����
	private int dataSize; // Stack�� ũ��.

	public Exp(int dataSize) {
		this.dataSize = dataSize;
		this.top = -1;
		this.data = new int[dataSize];
	}
	
	public void clear() {/*�迭�� �ʱ�ȭ ��Ų��.*/
		this.top = -1;
	}

	public void push(int value) {/*Stack�� �ϳ� �߰��Ѵ�. ����ġ�� �þ��.*/
		this.data[++this.top] = value;

	}

	public boolean isFull() { /*����ġ�� ��á���� Ȯ��.*/
		if (top == dataSize - 1) {
			return true;
		} else {
			return false;
		}
	}

	public int getTop() { /*���� ����ġ�� �󸶳� �׿����� �ҷ��´�.*/
		return (this.top);
	}
}

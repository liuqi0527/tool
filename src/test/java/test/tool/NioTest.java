package test.tool;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class NioTest {

	public static void main(String[] args){
		try {
			ServerSocketChannel channel = ServerSocketChannel.open();
			channel.configureBlocking(false);
			channel.bind(new InetSocketAddress(9988));
//			channel.socket().bind(new InetSocketAddress(9988));
			
			Selector selector = Selector.open();
			channel.register(selector, SelectionKey.OP_ACCEPT);
			
			while(true){
				selector.select();
				Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
				while(iter.hasNext()){
					SelectionKey key = iter.next();
					if(key.isAcceptable()){
						System.out.println("accept");
						channel.register(selector, SelectionKey.OP_READ);
					}else if(key.isReadable()){
						System.out.println("read");
					}
					iter.remove();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

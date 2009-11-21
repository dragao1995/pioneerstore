package test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.*; 
// import the JFlashPlayer package
import com.jpackages.jflashplayer.*;

/**
 * Example code demonstrating JFlashPlayer
 * 
 * Copyright (c) 2001-2009
 * Company: VersaEdge Software, LLC
 * Site: http://www.jpackages.com/jflashplayer
 */
public class FlashTest extends JFrame implements FlashPanelListener {
	// handle to a FlashPanel instance
	FlashPanel flashPanel;

	/**
	 * An example Java method that will be called from Flash ActionScript code
	 * using ExternalInterface.call.  These methods must be public to be accessible from Flash.
	 * 
	 * For this method to be callable from Flash, you must use the 
	 * FlashPanel.setFlashCallObject method as is done below in the creatFlashPanel method.
	 */
	public String[] testFunction(String text, boolean b, double d, String[] array) {
		// output some of the demo variables
		System.out.println("Flash has called testFunction");
		System.out.println(" received text: " + text);
		System.out.println(" received boolean: " + b);
		System.out.println(" received double: " + d);	
		for (int i=0; i < array.length; i++) {
			System.out.println(" received array[" + i + "]: " + array[i]);
		}		

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				String message = "Flash has executed ExternalInterface.call on Java testFunction method.";
				JOptionPane.showMessageDialog(null, message,
						"ExternalInterface.call executed", JOptionPane.PLAIN_MESSAGE);
			}			
		});
		return new String[] { "aloha" };  // change aloha to stop to cause the Flash movie to stop on this method call
	}

	/**
	 * Called from Flash by ExternalInterface.call to indicate mouse over and out events on a Flash symbol.
	 */
	public void notifyFlashMouseEvent(String event) {
		System.out.println("Flash mouse event: " + event);
	}
	
	/**
	 * This method is called from Flash to launch a web site.
	 */
	public void launchIE(String site) {
		try {
			System.out.println("launching IE to " + site);
			Runtime.getRuntime().exec("explorer " + site);
		} catch (Exception e) {
		}
	}
	
	/**
	 * FlashPanelListener event method which receives FSCommand Flash events.
	 * 
	 * You should use ExternalInterface.call instead of FSCommand
	 * with the latest ActionScript version.  Older ActionScript versions
	 * will only have access to FSCommand.
	 */
	public void FSCommand(String command, String arg) {
		System.out.println("FSCommand event received: " + command + " " + arg);

		if (command.equals("javaLink")) {
			launchIE(arg);
		} else if (command.equals("javaExecute")) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					JOptionPane.showMessageDialog(null,
						"Flash FSCommand events can be received by Java");
				}
			});
		}
	}
	
	
	
	/**
	 * Create a FlashPanel instance and add it to the frame
	 */
	void createFlashPanel() {
		// install Flash 10 if Flash 6 or greater is not present
		//FlashPanel.installFlash("9");
				
		String flashVersionRequired = "9";
		try {
			// if there is Flash 9 or greater present, show Movie.swf
			// otherwise show Movie-FSCommand.swf
			String flashFilePath = "d:/aa.swf";								
			//if (!FlashPanel.hasFlashVersion("9")) {
			//	flashFilePath = "example/Movie-FSCommand.swf";		
			//	flashVersionRequired = "6";	
			//}
			//FlashPanel.setRequiredFlashVersion(flashVersionRequired);

			// construct a FlashPanel displaying the SWF flash animation file
			flashPanel = new FlashPanel(new File(flashFilePath));					
		} catch (JFlashLibraryLoadFailedException e) {			
			exitError("A required library (DLL) is missing or damaged.");
		} catch (FileNotFoundException e) {
			exitError("Failed to find SWF file specified.");
		} catch (JFlashInvalidFlashException e) {
			exitError("Required version " + flashVersionRequired + " of Flash is not installed.");
		}

		// add the FlashPanel to the frame
		this.getContentPane().add(flashPanel, BorderLayout.CENTER);

		// specify the object for Flash ExternalInterface.call events to search for the called method on
		flashPanel.setFlashCallObject(this);		

		// specify the FlashPanelListener in case a movie is using the older FSCommand event
		flashPanel.addFlashPanelListener(this);

		// specify variables for a flash movie which are available from the start of the
		// movie as long as this is called before the FlashPanel is visible
		flashPanel.setVariables("myGreeting=hi&myNumber=1&myVar=good%20job");		
	}

	/**
	 * Constructor of Example Frame
	 */
	public FlashTest() {
		// define some frame information
		this.setTitle("JFlashPlayer Example from JPackages.com");
		this.setSize(600, 400);
		this.getContentPane().setLayout(new BorderLayout());

		createButtons();
		createFlashPanel();

		// exit the application when the frame closes
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		// make the Example Frame visible
		centerAndDisplay();
		
		// launch a thread to monitor the current frame
		FrameMonitorThread fmt = new FrameMonitorThread(flashPanel);
		fmt.start();
		
	}

	/**
	 * Select a different SWF file and display it in the FlashPanel
	 */
	void setMovieAction() {
		// filter to only allow SWF file choices
		FileFilter swfFileFilter = new FileFilter() {
			public boolean accept(File f) {
				if (f.isDirectory() || f.toString().endsWith(".swf"))
					return true;
				
				return false;
			}
			
			public String getDescription() {
				return "SWF Files";
			}
		};
		
		JFileChooser chooser = new JFileChooser("example");
		chooser.addChoosableFileFilter(swfFileFilter);
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			try {
				if (file.toString().endsWith(".swf")) {
					// specify the new SWF file to be displayed in the already visible FlashPanel
					flashPanel.setMovie(file);
					
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Instruct the flash movie to play.
	 */
	void playAction() {
		flashPanel.play();
		System.out.println("isPlaying=" + flashPanel.isPlaying());
	}

	/**
	 * Instruct the flash movie to stop playing.
	 */
	void stopAction() {
		flashPanel.stop();
		System.out.println("isPlaying=" + flashPanel.isPlaying());		
	}

	/**
	 * Instruct the flash movie to go back one frame.  
	 * This will also stop the movie if it was playing.
	 */
	void backAction() {
		flashPanel.back();
		System.out.println("currentFrame=" + flashPanel.getCurrentFrame());
	}

	/**
	 * Instruct the flash movie to go forward one frame.
	 * This will also stop the movie if it was playing.
	 */
	void forwardAction() {
		flashPanel.forward();
		System.out.println("currentFrame=" + flashPanel.getCurrentFrame());
	}

	/**
	 * Instruct the flash movie to rewind to the first frame.
	 * This will also stop the movie if it was playing.
	 */
	void rewindAction() {
		flashPanel.rewind();
		System.out.println("currentFrame=" + flashPanel.getCurrentFrame());
	}

	/**
	 * Select and set the flash movie background color.
	 */
	void backgroundAction() {
		Color c = JColorChooser.showDialog(this, "Choose Background",
				flashPanel.getBackground());
		flashPanel.setBackground(c);
	}

	/**
	 * Instruct the flash movie to loop or not.
	 */
	void loopAction(JCheckBox loopCheckBox) {
		flashPanel.setLoop(loopCheckBox.isSelected());
	}

	/**
	 * Call a flash function and display the return value.
	 */
	void callFlashAction() {
		// example arguments to pass to Flash
		String arg1 = "a string as first arg";
		Boolean arg2 = new Boolean(true); // change this to false to see a different response from Flash
		Double arg3 = new Double(3);
		String[] arg4 = new String[] { "this", "is", "an", "array" };

		// call the Flash function flashFunctionName as specified by ExternalInterface.addCallback
		// pass to the function arg1, arg2, arg3, and arg4
		String callResponse = (String) flashPanel.callFlashFunction(
				"flashFunctionName", new Object[] { arg1, arg2, arg3, arg4 });
		System.out.println("callResponse: " + callResponse);
		if (callResponse != null)
			JOptionPane.showMessageDialog(this, callResponse,
					"Flash responded with...", JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Define some buttons to demonstrate JFlashPlayer functionality
	 */
	void createButtons() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

		JButton setMovieButton = new JButton("Set Movie");
		setMovieButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setMovieAction();
			}
		});
		buttonPanel.add(setMovieButton);
		JButton playButton = new JButton("Play");
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playAction();
			}
		});
		buttonPanel.add(playButton);
		JButton stopButton = new JButton("Stop");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stopAction();
			}
		});
		buttonPanel.add(stopButton);
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				backAction();
			}
		});
		buttonPanel.add(backButton);
		JButton forwardButton = new JButton("Forward");
		forwardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				forwardAction();
			}
		});
		buttonPanel.add(forwardButton);
		JButton rewindButton = new JButton("Rewind");
		rewindButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rewindAction();
			}
		});
		buttonPanel.add(rewindButton);
		JButton backgroundButton = new JButton("Background");
		backgroundButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				backgroundAction();
			}
		});
		buttonPanel.add(backgroundButton);
		final JCheckBox loopCheckBox = new JCheckBox("Loop");
		loopCheckBox.setSelected(true);
		loopCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loopAction(loopCheckBox);
			}
		});
		buttonPanel.add(loopCheckBox);
		JButton callFlashButton = new JButton("Call Flash");
		callFlashButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				callFlashAction();
			}
		});
		buttonPanel.add(callFlashButton);
		
		// center all components
		for (int i = 0; i < buttonPanel.getComponentCount(); i++) {
			((JComponent) buttonPanel.getComponent(i))
					.setAlignmentX(Component.CENTER_ALIGNMENT);
		}				

		buttonPanel.add(currentFrameLabel);
		currentFrameLabel.setAlignment(Label.CENTER);
		
		this.getContentPane().add(buttonPanel, BorderLayout.WEST);
	}
	
	final Label currentFrameLabel = new Label("Frame: N/A");
	
	/**
	 * Thread to poll the current frame of the flash movie to be displayed
	 */
	class FrameMonitorThread extends Thread {
		FlashPanel flashPanel;
		
		FrameMonitorThread(FlashPanel fp) {
			flashPanel = fp;
		}
		
		public void run() {
			while (true) {
				if (flashPanel != null) {
					final long currentFrame = flashPanel.getCurrentFrame();
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							currentFrameLabel.setText("Frame: " + currentFrame);
						}						
					});
				}
				try {
					Thread.sleep(100);
				} catch (Exception e) {}
			}
		}
	}
	
	/**
	 * Called if no flash is present then exit application
	 */
	void exitError(String message) {
		JOptionPane.showMessageDialog(null, message,
				"Error", JOptionPane.ERROR_MESSAGE);
		System.exit(1);
	}
	
	/**
	 * Center the Frame and display it
	 */
	void centerAndDisplay() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		this.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);
		this.setVisible(true);
		this.toFront();
	}
	
	/**
	 * Main method defines the example frame
	 */
	public static void main(String[] args) {
		// For registered users, uncomment the following line and set your registration key
		//FlashPanel.setRegistrationKey("AAAA-BBBB-CCCC-DDDD-EEEE-FFFF");
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		FlashTest frame = new FlashTest();		
	}	
}

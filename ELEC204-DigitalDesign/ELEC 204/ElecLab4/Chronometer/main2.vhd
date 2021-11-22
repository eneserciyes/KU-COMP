----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    19:30:22 11/27/2019 
-- Design Name: 
-- Module Name:    main - Behavioral 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity main is
    Port ( S : IN STD_LOGIC;
	 B : IN STD_LOGIC;
	 clk : IN  STD_LOGIC;
	 rst : IN STD_LOGIC;
	 stp : IN STD_LOGIC;
	 SevSegControl : OUT STD_LOGIC_VECTOR (7 DOWNTO 0):=x"ff";
	 SevSegBus : OUT STD_LOGIC_VECTOR (7 DOWNTO 0)
	 );
end main;

architecture Behavioral of main is

COMPONENT clock_divider
	PORT(
		clk : IN std_logic;          
		DCT : OUT std_logic;
		DCC : OUT std_logic
		);
	END COMPONENT;
	
COMPONENT chronometer
	PORT(
		S : IN std_logic;
		B : IN std_logic;
		rst : IN std_logic;
		stp: IN STD_LOGIC;
		DCC : IN std_logic;          
		MiliSecRight : OUT std_logic_vector(3 downto 0);
		MiliSecLeft : OUT std_logic_vector(3 downto 0);
		SecRight : OUT std_logic_vector(3 downto 0);
		Secleft : OUT std_logic_vector(3 downto 0);
		MinRight : OUT std_logic_vector(3 downto 0);
		MinLeft : OUT std_logic_vector(3 downto 0)
		);
	END COMPONENT;

COMPONENT timer
	PORT(
		S : IN std_logic;
		B : IN std_logic;
		rst : IN std_logic;
		DCT : IN std_logic;          
		SecRight : OUT std_logic_vector(3 downto 0);
		SecLeft : OUT std_logic_vector(3 downto 0);
		MinRight : OUT std_logic_vector(3 downto 0);
		MinLeft : OUT std_logic_vector(3 downto 0);
		HourRight : OUT std_logic_vector(3 downto 0);
		Hourleft : OUT std_logic_vector(3 downto 0)
		);
	END COMPONENT;
	
COMPONENT sevenSegment
	PORT(
		A : IN std_logic_vector(3 downto 0);
		B : IN std_logic_vector(3 downto 0);
		C : IN std_logic_vector(3 downto 0);
		D : IN std_logic_vector(3 downto 0);
		E : IN std_logic_vector(3 downto 0);
		F : IN std_logic_vector(3 downto 0);
		G : IN std_logic_vector(3 downto 0);
		H : IN std_logic_vector(3 downto 0);
		clk : IN std_logic;          
		SevenSegControl : OUT std_logic_vector(7 downto 0);
		SevenSegBus : OUT std_logic_vector(7 downto 0)
		);
	END COMPONENT;
	
signal seven_segment_1: std_logic_vector (3 downto 0);
signal seven_segment_2: std_logic_vector (3 downto 0);
signal seven_segment_3: std_logic_vector (3 downto 0);
signal seven_segment_4: std_logic_vector (3 downto 0);
signal seven_segment_5: std_logic_vector (3 downto 0);
signal seven_segment_6: std_logic_vector (3 downto 0);
signal seven_segment_7: std_logic_vector (3 downto 0);
signal seven_segment_8: std_logic_vector (3 downto 0);
signal DCT: std_logic;
signal DCC: std_logic;
signal miliSecL: std_logic_vector (3 downto 0);
signal miliSecR: std_logic_vector (3 downto 0);
signal secondL_ch: std_logic_vector (3 downto 0);
signal secondR_ch: std_logic_vector (3 downto 0);
signal secondL_tm: std_logic_vector (3 downto 0);
signal secondR_tm: std_logic_vector (3 downto 0);
signal minuteL_ch: std_logic_vector (3 downto 0);
signal minuteR_ch: std_logic_vector (3 downto 0);
signal minuteL_tm: std_logic_vector (3 downto 0);
signal minuteR_tm: std_logic_vector (3 downto 0);
signal hourL: std_logic_vector (3 downto 0);
signal hourR: std_logic_vector (3 downto 0);

begin

	Inst_clock_divider: clock_divider PORT MAP(
		clk => clk,
		DCT => DCT,
		DCC => DCC
	);


	Inst_chronometer: chronometer PORT MAP(
		S => S,
		B => B,
		rst => rst,
		stp => stp,
		DCC => DCC,
		MiliSecRight => miliSecR,
		MiliSecLeft => miliSecL,
		SecRight => secondR_ch,
		Secleft => secondL_ch,
		MinRight => minuteR_ch,
		MinLeft => minuteL_ch 
	);
	

	Inst_timer: timer PORT MAP(
		S => S,
		B => B,
		rst => rst,
		DCT => DCT,
		SecRight => secondR_tm,
		SecLeft => secondL_tm,
		MinRight => minuteR_tm,
		MinLeft => minuteL_tm,
		HourRight => hourR,
		Hourleft => hourL
	);

	process(S,miliSecR,miliSecL,secondR_ch,secondL_ch,minuteR_ch,minuteL_ch,
	secondR_tm,secondL_tm,minuteR_tm,minuteL_tm,hourR,hourL)
	begin
	if (S='1') then
		seven_segment_1<=miliSecR;
		seven_segment_2<=miliSecL;
		seven_segment_4<=secondR_ch;
		seven_segment_5<=secondL_ch;
		seven_segment_7<=minuteR_ch;
		seven_segment_8<=minuteL_ch;
	else
		seven_segment_1<=secondR_tm;
		seven_segment_2<=secondL_tm;
		seven_segment_4<=minuteR_tm;
		seven_segment_5<=minuteL_tm;
		seven_segment_7<=hourR;
		seven_segment_8<=hourL;
	end if;
	end process;
	
	seven_segment_3<=x"a";
	seven_segment_6<=x"a";
	
	Inst_sevenSegment: sevenSegment PORT MAP(
		A => seven_segment_1,
		B => seven_segment_2,
		C => seven_segment_3,
		D => seven_segment_4,
		E => seven_segment_5,
		F => seven_segment_6,
		G => seven_segment_7,
		H => seven_segment_8,
		clk => clk,
		SevenSegControl => SevSegControl,
		SevenSegBus => SevSegBus
	);

end Behavioral;


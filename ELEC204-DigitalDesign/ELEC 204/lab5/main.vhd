----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    23:40:36 12/11/2019 
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
use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity main is
Port(
	clk: in std_logic;
	start: in std_logic;
	reset: in std_logic :='0';
	state_binary: out std_logic_vector(1 downto 0);
	dec_led: out std_logic;
	zero_led: out std_logic;
	LEDs: out std_logic_vector(3 downto 0);
	test_led: out std_logic);
end main;


architecture Behavioral of main is
	COMPONENT washing_machine
	PORT(
		clk : IN std_logic;
		start : IN std_logic;
		reset : IN std_logic;          
		state : OUT std_logic_vector(3 downto 0);
		dec_out : out std_logic;
		zero_out: out std_logic;
		curr_state_binary: out std_logic_vector(1 downto 0);
		test: out std_logic
		);
	END COMPONENT;

signal curr_state : std_logic_vector(1 downto 0) :="11";
signal counter : std_logic_vector(2 downto 0) := "000";
signal dec : std_logic :='0';
signal zero : std_logic :='0';
signal start_debounced : std_logic := '0';
signal slowClock: std_logic;


begin

Inst_washing_machine: washing_machine PORT MAP(
	clk => clk ,
	start => start,
	reset => reset,
	state => LEDs,
	dec_out => dec_led,
	zero_out => zero_led,
	curr_state_binary => state_binary,
	test => test_led
);


end Behavioral;


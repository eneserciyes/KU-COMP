----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    00:44:56 12/12/2019 
-- Design Name: 
-- Module Name:    washing_machine - Behavioral 
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

entity washing_machine is
Port(
	clk: in std_logic;
	start: in std_logic;
	reset: in std_logic;
	state: out std_logic_vector(3 downto 0);
	dec_out : out std_logic;
	zero_out: out std_logic;
	curr_state_binary: out std_logic_vector(1 downto 0);
	test: out std_logic);
end washing_machine;


architecture Behavioral of washing_machine is
--
--	COMPONENT counter
--	PORT(
--		clk : IN std_logic;
--		dec : IN std_logic;
--		counter_init : IN std_logic_vector(2 downto 0);          
--		zero : OUT std_logic
--		);
--	END COMPONENT;

	COMPONENT slowerClock
	PORT(
		clk : IN std_logic;          
		SlowClock : OUT std_logic
		);
	END COMPONENT;
	
signal curr_state : std_logic_vector(1 downto 0) :="11";
signal counter_init : std_logic_vector(31 downto 0) := (others => '0');
signal dec : std_logic :='0';
signal zero : std_logic :='0';
signal start_debounced : std_logic;
signal slowClock: std_logic;
begin

--Inst_counter: counter PORT MAP(
--	clk => slowClock,
--	dec => dec,
--	counter_init => counter_init,
--	zero => zero
--);

--Inst_slowerClock: slowerClock PORT MAP(
--	clk => clk,
--	SlowClock => slowClock
--);



process(clk, start, reset)
begin
if rising_edge(clk) then
	if curr_state = "11" and start = '1' then
		counter_init <= x"17D78400";
		curr_state <= "00";
		dec <= '1';
		start_debounced <= '1';
	end if;
	
	if dec = '1' then
		if counter_init = x"00000000" then 
			zero <= '1';
		else
			counter_init <= std_logic_vector(unsigned(counter_init) - 1);
			zero<= '0';
		end if;
	end if;

	if zero = '1' and start_debounced = '1' then
		dec <= '0';
		if curr_state = "00" then 
			curr_state <= "01";
			counter_init <= x"11E1A300";
			zero<='0';
			dec <= '1';
		elsif curr_state = "01" then
			curr_state <= "10";
			counter_init <= x"0BEBC200";
			zero<='0';
			dec <= '1';
		elsif curr_state = "10" then
			curr_state <= "11";
			counter_init <= x"00000000";
			zero<='1';
			start_debounced <='0';
		end if;
	end if;
	
	if reset = '1' then
		dec<='0';
		curr_state<= "11";
		counter_init<=(others => '0');
		start_debounced <= '0';
	end if;
end if;
end process;

with curr_state select state <= 
	"1000" when "11",
	"0001" when "00",
	"0010" when "01",
	"0100" when "10",
	"0000" when others;

curr_state_binary <= curr_state;
dec_out <= dec;
zero_out <= zero;
end Behavioral;



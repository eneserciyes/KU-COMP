----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    17:05:21 10/14/2017 
-- Design Name: 
-- Module Name:    slower_clock - Behavioral 
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
use ieee.std_logic_unsigned.all;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity slowerClock is
port(
		 clk: in std_logic; 
		 SlowClock: out std_logic);
end slowerClock;

architecture Behavioral of slowerClock is
signal clkCounter: std_logic_vector(27 downto 0):= x"0000000";
signal temp: std_logic :='0';

begin
Process_clk: process(clk)
begin
if(rising_edge(clk)) then
clkCounter <= clkCounter + "10000000";
	if (clkCounter < x"1FF1FF") then
		Temp <= '1';
		else 
		clkCounter <= x"0000000";
		Temp <='0';
	end if;
end if;
end process;
slowClock <= temp;
end Behavioral;


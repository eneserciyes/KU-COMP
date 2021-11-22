----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    17:53:08 10/14/2017 
-- Design Name: 
-- Module Name:    driver - Behavioral 
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
use ieee.std_logic_arith.all;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity driver is
port(clk: in std_logic; 
		A: in std_logic_vector(3 downto 0);
		B: in std_logic_vector(3 downto 0);
		C: in std_logic_vector(3 downto 0);
		D: in std_logic_vector (3 downto 0);
		E: in std_logic_vector (3 downto 0);
		F: in std_logic_vector (3 downto 0);
		G: in std_logic_vector (3 downto 0);
		H: in std_logic_vector (3 downto 0);
		sevenSegNumber: out std_logic_vector(7 downto 0); 
		sevenSegValue: out std_logic_vector(3 downto 0));
end driver;

architecture Behavioral of driver is
signal counter: std_logic_vector(2 downto 0):="000";
begin
mainprocess: process
begin
	if (clk'event and clk='1' ) then 
		counter <= counter + 1;
	end if;

end process;
with counter select sevenSegNumber <=
				"11111110" when "111",
				"11111101" when "110",
				"11111011" when "101",
				"11110111" when "100",
				"11101111" when "011", 
				"11011111" when "010",
				"10111111" when "001", 
				"01111111" when "000",
				"11111111" when others; 

with counter select sevenSegValue <= 
					A when "111", 
					B when "110", 
					C when "101", 
					D when "100",
					E when "011",
					F when "010",
					G when "001",
					H when "000",
					"1101" when others;
end Behavioral;


----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    18:31:20 10/14/2017 
-- Design Name: 
-- Module Name:    Decoder - Behavioral 
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

entity decoder is
port(
		inValue: in std_logic_vector(3 downto 0); 
		outValue: out std_logic_vector(7 downto 0));
end decoder;

architecture Behavioral of decoder is

begin
with inValue select outValue <= 
		"00000011" when x"0", 
		"10011111" when x"1", 
		"00100101" when x"2", 
		"00001101" when x"3", 
		"10011001" when x"4", 
		"01001001" when x"5", 
		"01000001" when x"6", 
		"00011111" when x"7", 
		"00000001" when x"8", 
		"00001001" when x"9", 
		"11111101" when x"a", 
		"11111110" when x"b", 
		"11111111" when others;

end Behavioral;


----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    23:14:18 12/12/2019 
-- Design Name: 
-- Module Name:    counter - Behavioral 
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

entity counter is
port(
	clk : in std_logic;
	dec : in std_logic;
	counter_init : in std_logic_vector(2 downto 0);
	zero: out std_logic
);
end counter;

architecture Behavioral of counter is
signal counter: std_logic_vector(2 downto 0) :=counter_init;
begin

process(clk, dec,clk,counter)
begin
	if rising_edge(clk) then
		if dec = '1' then
			if counter = "000" then 
				zero <= '1';
			else
				counter <= std_logic_vector(unsigned(counter) - 1);
			end if;
		else
			zero <= '0';
		end if;
	end if;
end process;

end Behavioral;


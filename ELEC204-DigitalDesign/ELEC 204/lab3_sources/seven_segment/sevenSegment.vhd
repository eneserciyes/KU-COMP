----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    09:27:52 10/13/2017 
-- Design Name: 
-- Module Name:    aa - Behavioral 
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
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity sevenSegment is
port( A: in std_logic_vector(3 downto 0);
		B: in std_logic_vector(3 downto 0);
		C: in std_logic_vector(3 downto 0);
		D: in std_logic_vector(3 downto 0);
		E: in std_logic_vector(3 downto 0);
		F: in std_logic_vector(3 downto 0);
		G: in std_logic_vector(3 downto 0);
		H: in std_logic_vector(3 downto 0);
		clk:in std_logic; 
		SevenSegControl: out std_logic_vector (7 downto 0):=x"ff";
		SevenSegBus: out std_logic_vector (7 downto 0));
end sevenSegment;

architecture Behavioral of sevenSegment is
signal clkTrigger: std_logic;
signal sevenSegValue: std_logic_vector(3 downto 0);

begin
SlowClock: entity work.slowerClock port map(
		clk=>clk, 
		slowClock => clkTrigger);

Driver: entity work.driver port map ( 
		clk=>clkTrigger, 
		A=>A, 
		B=>B, 
		C=>C, 
		D=>D, 
		E=>E,
		F=>F,
		G=>G,
		H=>H,
		sevenSegValue => sevenSegValue, 
		sevenSegNumber => sevenSegControl);

Decoder: entity work.decoder port map (
		inValue =>sevenSegValue,  
		outValue => sevenSegBus);


end Behavioral;

